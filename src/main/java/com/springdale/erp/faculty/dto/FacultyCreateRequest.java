package com.springdale.erp.faculty.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record FacultyCreateRequest(
        @NotBlank String fullName,
        @NotBlank @Email String email,
        String phone,
        @NotBlank @Size(min = 8, max = 64) String password,
        @NotBlank String employeeCode,
        @NotBlank String department,
        String designation,
        String qualification,
        LocalDate joiningDate,
        List<FacultySubjectRequest> subjects
) {
    public record FacultySubjectRequest(
            @NotBlank String subjectName,
            @NotBlank String className
    ) {}
}
