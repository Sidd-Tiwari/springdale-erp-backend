package com.springdale.erp.fees.dto;

import java.math.BigDecimal;

public record FeeStructureDto(
        Long id,
        String className,
        String academicYear,
        BigDecimal tuitionFee,
        BigDecimal transportFee,
        BigDecimal examFee,
        BigDecimal miscFee,
        BigDecimal totalFee
) {
}
