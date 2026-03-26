package com.springdale.erp.faculty.dto;

import java.time.LocalDate;
import java.util.List;

public record FacultyDto(
        Long id,
        String fullName,
        String email,
        String phone,
        String employeeCode,
        String department,
        String designation,
        String qualification,
        LocalDate joiningDate,
        List<FacultySubjectDto> subjects
) {
    public record FacultySubjectDto(String subjectName, String className) {}
}
