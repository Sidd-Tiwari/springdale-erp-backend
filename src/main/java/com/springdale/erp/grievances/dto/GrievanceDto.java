package com.springdale.erp.grievances.dto;

import jakarta.validation.constraints.NotBlank;

public record GrievanceDto(
        Long id,
        @NotBlank String subjectLine,
        @NotBlank String descriptionText,
        String status,
        Long raisedByUserId,
        String raisedByName
) {
}
