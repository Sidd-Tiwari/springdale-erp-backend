package com.springdale.erp.reports.service;

import com.springdale.erp.fees.enums.PaymentStatus;
import com.springdale.erp.fees.repo.FeePaymentRepository;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeeReportService {

    private final FeePaymentRepository feePaymentRepository;

    public FeeReportService(FeePaymentRepository feePaymentRepository) {
        this.feePaymentRepository = feePaymentRepository;
    }

    public Map<String, Object> monthlyCollection(String feeMonth) {
        var records = feePaymentRepository.findByFeeMonthAndStatus(feeMonth, PaymentStatus.PAID);
        BigDecimal total = records.stream()
                .map(payment -> payment.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                "feeMonth", feeMonth,
                "transactions", records.size(),
                "totalCollection", total
        );
    }
}
