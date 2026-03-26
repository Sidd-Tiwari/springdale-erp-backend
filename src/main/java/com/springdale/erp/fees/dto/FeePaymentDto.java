package com.springdale.erp.fees.dto;

import com.springdale.erp.fees.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record FeePaymentDto(
        Long id,
        Long studentId,
        String studentName,
        String admissionNo,
        String feeMonth,
        BigDecimal amount,
        PaymentStatus status,
        LocalDate paymentDate,
        String paymentMode,
        String transactionReference,
        String receiptNo,
        String remarks
) {
}
