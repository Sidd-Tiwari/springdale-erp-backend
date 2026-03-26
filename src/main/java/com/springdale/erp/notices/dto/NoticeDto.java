package com.springdale.erp.notices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record NoticeDto(
        Long id,
        @NotBlank String noticeTitle,
        @NotBlank String noticeBody,
        String targetRole,
        @NotNull LocalDate publishedDate,
        boolean active
) {
}
