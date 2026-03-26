package com.springdale.erp.exams.repo;

import com.springdale.erp.exams.entity.Exam;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByClassNameAndAcademicYear(String className, String academicYear);
}
