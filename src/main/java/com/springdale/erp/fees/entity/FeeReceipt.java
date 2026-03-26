package com.springdale.erp.fees.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "fee_receipts", indexes = {
        @Index(name = "idx_fee_receipt_receipt_no", columnList = "receipt_no", unique = true)
})
public class FeeReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_no", nullable = false, unique = true, length = 50)
    private String receiptNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_payment_id", nullable = false, unique = true)
    private FeePayment feePayment;

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt;

    @PrePersist
    public void onCreate() {
        this.generatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getReceiptNo() { return receiptNo; }
    public void setReceiptNo(String receiptNo) { this.receiptNo = receiptNo; }
    public FeePayment getFeePayment() { return feePayment; }
    public void setFeePayment(FeePayment feePayment) { this.feePayment = feePayment; }
}
