package com.springdale.erp.exams.repo;

import com.springdale.erp.exams.entity.ExamResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findByExamId(Long examId);
    List<ExamResult> findByExamIdAndStudentId(Long examId, Long studentId);
}
