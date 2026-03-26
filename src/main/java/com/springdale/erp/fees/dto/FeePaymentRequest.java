package com.springdale.erp.fees.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record FeePaymentRequest(
        @NotNull Long studentId,
        @NotBlank String feeMonth,
        @NotNull @DecimalMin("0.0") BigDecimal amount,
        @NotNull LocalDate paymentDate,
        @NotBlank String paymentMode,
        @NotBlank String transactionReference,
        String remarks
) {
}
