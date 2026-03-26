package com.springdale.erp.fees.repo;

import com.springdale.erp.fees.entity.FeePayment;
import com.springdale.erp.fees.enums.PaymentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    Optional<FeePayment> findByStudentIdAndFeeMonth(Long studentId, String feeMonth);

    List<FeePayment> findByStudentId(Long studentId);

    List<FeePayment> findByFeeMonthAndStatus(String feeMonth, PaymentStatus status);
}
