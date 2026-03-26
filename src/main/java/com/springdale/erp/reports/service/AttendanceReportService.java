package com.springdale.erp.reports.service;

import com.springdale.erp.attendance.enums.AttendanceStatus;
import com.springdale.erp.attendance.repo.AttendanceRepository;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AttendanceReportService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceReportService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Map<String, Object> dailySummary(LocalDate date) {
        var records = attendanceRepository.findByAttendanceDate(date);
        return Map.of(
                "date", date,
                "total", records.size(),
                "present", records.stream().filter(r -> r.getStatus() == AttendanceStatus.PRESENT).count(),
                "absent", records.stream().filter(r -> r.getStatus() == AttendanceStatus.ABSENT).count(),
                "late", records.stream().filter(r -> r.getStatus() == AttendanceStatus.LATE).count(),
                "leave", records.stream().filter(r -> r.getStatus() == AttendanceStatus.LEAVE).count()
        );
    }
}
