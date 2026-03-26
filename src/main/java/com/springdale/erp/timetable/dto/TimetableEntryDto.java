package com.springdale.erp.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record TimetableEntryDto(
        Long id,
        @NotBlank String className,
        @NotBlank String section,
        @NotNull DayOfWeek dayOfWeek,
        @NotBlank String subjectName,
        @NotBlank String facultyName,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime
) {
}
