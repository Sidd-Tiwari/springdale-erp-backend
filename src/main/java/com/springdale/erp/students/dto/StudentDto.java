package com.springdale.erp.students.dto;

import java.time.LocalDate;

public record StudentDto(
        Long id,
        String fullName,
        String email,
        String phone,
        String admissionNo,
        String className,
        String section,
        Integer rollNumber,
        LocalDate dateOfBirth,
        String gender,
        String address,
        String academicYear,
        GuardianDto guardian
) {
    public record GuardianDto(Long id, String guardianName, String relationship, String phone, String email) {}
}
