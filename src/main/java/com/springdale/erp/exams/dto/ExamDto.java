package com.springdale.erp.exams.dto;

import java.time.LocalDate;
import java.util.List;

public record ExamDto(
        Long id,
        String examName,
        String className,
        String academicYear,
        LocalDate startDate,
        LocalDate endDate,
        List<ExamScheduleDto> schedules
) {
}
