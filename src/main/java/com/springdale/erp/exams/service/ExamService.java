package com.springdale.erp.exams.service;

import com.springdale.erp.common.exception.ResourceNotFoundException;
import com.springdale.erp.exams.dto.*;
import com.springdale.erp.exams.entity.Exam;
import com.springdale.erp.exams.entity.ExamResult;
import com.springdale.erp.exams.entity.ExamSchedule;
import com.springdale.erp.exams.repo.ExamRepository;
import com.springdale.erp.exams.repo.ExamResultRepository;
import com.springdale.erp.students.entity.Student;
import com.springdale.erp.students.repo.StudentRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;

    public ExamService(ExamRepository examRepository,
                       ExamResultRepository examResultRepository,
                       StudentRepository studentRepository) {
        this.examRepository = examRepository;
        this.examResultRepository = examResultRepository;
        this.studentRepository = studentRepository;
    }

    public ExamDto create(ExamDto request) {
        Exam exam = new Exam();
        exam.setExamName(request.examName());
        exam.setClassName(request.className());
        exam.setAcademicYear(request.academicYear());
        exam.setStartDate(request.startDate());
        exam.setEndDate(request.endDate());

        if (request.schedules() != null) {
            request.schedules().forEach(scheduleDto -> {
                ExamSchedule schedule = new ExamSchedule();
                schedule.setExam(exam);
                schedule.setSubjectName(scheduleDto.subjectName());
                schedule.setExamDate(scheduleDto.examDate());
                schedule.setStartTime(scheduleDto.startTime());
                schedule.setEndTime(scheduleDto.endTime());
                exam.getSchedules().add(schedule);
            });
        }

        return toDto(examRepository.save(exam));
    }

    @Transactional(readOnly = true)
    public List<ExamDto> list(String className, String academicYear) {
        return examRepository.findByClassNameAndAcademicYear(className, academicYear).stream()
                .map(this::toDto)
                .toList();
    }

    public ExamResultDto recordResult(Long examId, Long studentId, ExamResultDto request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        ExamResult result = new ExamResult();
        result.setExam(exam);
        result.setStudent(student);
        result.setSubjectName(request.subjectName());
        result.setMaxMarks(request.maxMarks());
        result.setObtainedMarks(request.obtainedMarks());

        return toResultDto(examResultRepository.save(result));
    }

    @Transactional(readOnly = true)
    public MarksheetDto marksheet(Long examId, Long studentId) {
        List<ExamResult> results = examResultRepository.findByExamIdAndStudentId(examId, studentId);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No results found for exam/student");
        }

        BigDecimal totalMarks = results.stream()
                .map(ExamResult::getMaxMarks)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal obtained = results.stream()
                .map(ExamResult::getObtainedMarks)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal percentage = totalMarks.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : obtained.multiply(BigDecimal.valueOf(100)).divide(totalMarks, 2, java.math.RoundingMode.HALF_UP);

        return new MarksheetDto(
                studentId,
                results.get(0).getStudent().getFullName(),
                results.get(0).getExam().getExamName(),
                results.stream().map(this::toResultDto).toList(),
                totalMarks,
                obtained,
                percentage
        );
    }

    private ExamDto toDto(Exam exam) {
        return new ExamDto(
                exam.getId(),
                exam.getExamName(),
                exam.getClassName(),
                exam.getAcademicYear(),
                exam.getStartDate(),
                exam.getEndDate(),
                exam.getSchedules().stream()
                        .map(s -> new ExamScheduleDto(s.getId(), s.getSubjectName(), s.getExamDate(), s.getStartTime(), s.getEndTime()))
                        .toList()
        );
    }

    private ExamResultDto toResultDto(ExamResult result) {
        BigDecimal percentage = result.getMaxMarks().compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : result.getObtainedMarks().multiply(BigDecimal.valueOf(100))
                .divide(result.getMaxMarks(), 2, java.math.RoundingMode.HALF_UP);

        return new ExamResultDto(
                result.getId(),
                result.getStudent().getId(),
                result.getStudent().getFullName(),
                result.getSubjectName(),
                result.getMaxMarks(),
                result.getObtainedMarks(),
                percentage
        );
    }
}
