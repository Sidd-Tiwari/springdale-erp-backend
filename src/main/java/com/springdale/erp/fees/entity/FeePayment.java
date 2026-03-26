package com.springdale.erp.fees.entity;

import com.springdale.erp.fees.enums.PaymentStatus;
import com.springdale.erp.students.entity.Student;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "fee_payments", indexes = {
        @Index(name = "idx_fee_payment_student_month", columnList = "student_id, fee_month"),
        @Index(name = "idx_fee_payment_status", columnList = "status")
})
public class FeePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "fee_month", nullable = false, length = 7)
    private String feeMonth;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_mode", length = 30)
    private String paymentMode;

    @Column(name = "transaction_ref", length = 100)
    private String transactionReference;

    @Column(length = 255)
    private String remarks;

    @OneToOne(mappedBy = "feePayment", cascade = CascadeType.ALL, orphanRemoval = true)
    private FeeReceipt receipt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getFeeMonth() { return feeMonth; }
    public void setFeeMonth(String feeMonth) { this.feeMonth = feeMonth; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public FeeReceipt getReceipt() { return receipt; }
    public void setReceipt(FeeReceipt receipt) { this.receipt = receipt; }
}
