package com.springdale.erp.exams.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamScheduleDto(
        Long id,
        String subjectName,
        LocalDate examDate,
        LocalTime startTime,
        LocalTime endTime
) {
}
