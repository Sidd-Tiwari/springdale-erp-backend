package com.springdale.erp.exams.dto;

import java.math.BigDecimal;

public record ExamResultDto(
        Long id,
        Long studentId,
        String studentName,
        String subjectName,
        BigDecimal maxMarks,
        BigDecimal obtainedMarks,
        BigDecimal percentage
) {
}
