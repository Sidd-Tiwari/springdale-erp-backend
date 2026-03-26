package com.springdale.erp.students.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record StudentUpdateRequest(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email String email,
        @Size(max = 20) String phone,
        @NotBlank @Size(max = 50) String className,
        @NotBlank @Size(max = 10) String section,
        @NotNull @Min(1) Integer rollNumber,
        LocalDate dateOfBirth,
        @Size(max = 20) String gender,
        @Size(max = 255) String address,
        @NotBlank @Size(max = 20) String academicYear,
        @NotBlank String guardianName,
        @NotBlank String guardianRelationship,
        @Size(max = 20) String guardianPhone,
        @Email String guardianEmail,
        boolean active
) {
}
