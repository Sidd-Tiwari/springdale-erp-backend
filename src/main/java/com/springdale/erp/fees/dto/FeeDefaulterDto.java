package com.springdale.erp.fees.dto;

import java.math.BigDecimal;

public record FeeDefaulterDto(
        Long studentId,
        String studentName,
        String admissionNo,
        String className,
        String feeMonth,
        BigDecimal expectedAmount,
        BigDecimal paidAmount,
        BigDecimal pendingAmount
) {
}
