package com.springdale.erp.exams.dto;

import java.math.BigDecimal;
import java.util.List;

public record MarksheetDto(
        Long studentId,
        String studentName,
        String examName,
        List<ExamResultDto> results,
        BigDecimal totalMarks,
        BigDecimal obtainedMarks,
        BigDecimal percentage
) {
}
