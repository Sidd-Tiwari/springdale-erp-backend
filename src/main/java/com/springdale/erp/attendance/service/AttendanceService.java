package com.springdale.erp.attendance.service;

import com.springdale.erp.attendance.dto.AttendanceMarkRequest;
import com.springdale.erp.attendance.dto.AttendanceRecord;
import com.springdale.erp.attendance.dto.AttendanceSummaryDto;
import com.springdale.erp.attendance.entity.Attendance;
import com.springdale.erp.attendance.enums.AttendanceStatus;
import com.springdale.erp.attendance.repo.AttendanceRepository;
import com.springdale.erp.students.entity.Student;
import com.springdale.erp.students.service.StudentService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentService studentService;

    public AttendanceService(AttendanceRepository attendanceRepository, StudentService studentService) {
        this.attendanceRepository = attendanceRepository;
        this.studentService = studentService;
    }

    public List<AttendanceRecord> markAttendance(AttendanceMarkRequest request) {
        for (AttendanceMarkRequest.AttendanceItem item : request.records()) {
            Student student = studentService.findEntity(item.studentId());
            Attendance attendance = attendanceRepository.findByStudentAndAttendanceDate(student, request.attendanceDate())
                    .orElseGet(Attendance::new);

            attendance.setStudent(student);
            attendance.setAttendanceDate(request.attendanceDate());
            attendance.setStatus(item.status());
            attendance.setRemarks(item.remarks());
            attendanceRepository.save(attendance);
        }
        return getByDate(request.attendanceDate());
    }

    @Transactional(readOnly = true)
    public List<AttendanceRecord> getByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date).stream()
                .map(this::toRecord)
                .toList();
    }

    @Transactional(readOnly = true)
    public AttendanceSummaryDto getSummary(Long studentId, LocalDate from, LocalDate to) {
        List<Attendance> records = attendanceRepository.findByStudentIdAndAttendanceDateBetween(studentId, from, to);
        String studentName = records.isEmpty() ? studentService.findEntity(studentId).getFullName() : records.get(0).getStudent().getFullName();

        long present = records.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent = records.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
        long late = records.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
        long leave = records.stream().filter(a -> a.getStatus() == AttendanceStatus.LEAVE).count();

        return new AttendanceSummaryDto(studentId, studentName, present, absent, late, leave, records.size());
    }

    private AttendanceRecord toRecord(Attendance attendance) {
        return new AttendanceRecord(
                attendance.getId(),
                attendance.getStudent().getId(),
                attendance.getStudent().getFullName(),
                attendance.getStudent().getAdmissionNo(),
                attendance.getAttendanceDate(),
                attendance.getStatus(),
                attendance.getRemarks()
        );
    }
}
