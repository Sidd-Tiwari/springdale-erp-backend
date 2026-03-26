package com.springdale.erp.fees.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fee_structures", indexes = {
        @Index(name = "idx_fee_structure_class_year", columnList = "class_name, academic_year", unique = true)
})
public class FeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name", nullable = false, length = 50)
    private String className;

    @Column(name = "academic_year", nullable = false, length = 20)
    private String academicYear;

    @Column(name = "tuition_fee", nullable = false, precision = 12, scale = 2)
    private BigDecimal tuitionFee;

    @Column(name = "transport_fee", precision = 12, scale = 2)
    private BigDecimal transportFee = BigDecimal.ZERO;

    @Column(name = "exam_fee", precision = 12, scale = 2)
    private BigDecimal examFee = BigDecimal.ZERO;

    @Column(name = "misc_fee", precision = 12, scale = 2)
    private BigDecimal miscFee = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public BigDecimal getTuitionFee() { return tuitionFee; }
    public void setTuitionFee(BigDecimal tuitionFee) { this.tuitionFee = tuitionFee; }
    public BigDecimal getTransportFee() { return transportFee; }
    public void setTransportFee(BigDecimal transportFee) { this.transportFee = transportFee; }
    public BigDecimal getExamFee() { return examFee; }
    public void setExamFee(BigDecimal examFee) { this.examFee = examFee; }
    public BigDecimal getMiscFee() { return miscFee; }
    public void setMiscFee(BigDecimal miscFee) { this.miscFee = miscFee; }
}
