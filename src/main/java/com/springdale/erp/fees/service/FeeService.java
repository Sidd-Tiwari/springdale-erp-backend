package com.springdale.erp.fees.service;

import com.springdale.erp.common.exception.ResourceNotFoundException;
import com.springdale.erp.fees.dto.*;
import com.springdale.erp.fees.entity.FeePayment;
import com.springdale.erp.fees.entity.FeeReceipt;
import com.springdale.erp.fees.entity.FeeStructure;
import com.springdale.erp.fees.enums.PaymentStatus;
import com.springdale.erp.fees.repo.FeePaymentRepository;
import com.springdale.erp.fees.repo.FeeStructureRepository;
import com.springdale.erp.students.entity.Student;
import com.springdale.erp.students.repo.StudentRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeeService {

    private final FeeStructureRepository feeStructureRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final StudentRepository studentRepository;

    public FeeService(FeeStructureRepository feeStructureRepository,
                      FeePaymentRepository feePaymentRepository,
                      StudentRepository studentRepository) {
        this.feeStructureRepository = feeStructureRepository;
        this.feePaymentRepository = feePaymentRepository;
        this.studentRepository = studentRepository;
    }

    public FeeStructureDto upsertStructure(FeeStructureDto request) {
        FeeStructure structure = feeStructureRepository.findByClassNameAndAcademicYear(request.className(), request.academicYear())
                .orElseGet(FeeStructure::new);

        structure.setClassName(request.className());
        structure.setAcademicYear(request.academicYear());
        structure.setTuitionFee(request.tuitionFee());
        structure.setTransportFee(request.transportFee());
        structure.setExamFee(request.examFee());
        structure.setMiscFee(request.miscFee());

        return toDto(feeStructureRepository.save(structure));
    }

    @Transactional(readOnly = true)
    public List<FeeStructureDto> getStructures() {
        return feeStructureRepository.findAll().stream().map(this::toDto).toList();
    }

    public FeePaymentDto recordPayment(FeePaymentRequest request) {
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.studentId()));

        FeePayment payment = feePaymentRepository.findByStudentIdAndFeeMonth(student.getId(), request.feeMonth())
                .orElseGet(FeePayment::new);

        payment.setStudent(student);
        payment.setFeeMonth(request.feeMonth());
        payment.setAmount(request.amount());
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(request.paymentDate());
        payment.setPaymentMode(request.paymentMode());
        payment.setTransactionReference(request.transactionReference());
        payment.setRemarks(request.remarks());

        FeeReceipt receipt = payment.getReceipt();
        if (receipt == null) {
            receipt = new FeeReceipt();
            receipt.setFeePayment(payment);
            receipt.setReceiptNo("REC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            payment.setReceipt(receipt);
        }

        return toPaymentDto(feePaymentRepository.save(payment));
    }

    @Transactional(readOnly = true)
    public List<FeePaymentDto> getPaymentsByStudent(Long studentId) {
        return feePaymentRepository.findByStudentId(studentId).stream().map(this::toPaymentDto).toList();
    }

    @Transactional(readOnly = true)
    public List<FeeDefaulterDto> getDefaulters(String feeMonth, String className, String academicYear) {
        List<Student> students = studentRepository.findAll((root, query, cb) -> cb.and(
                cb.equal(root.get("className"), className),
                cb.equal(root.get("academicYear"), academicYear),
                cb.equal(root.get("active"), true)
        ));

        FeeStructure structure = feeStructureRepository.findByClassNameAndAcademicYear(className, academicYear)
                .orElseThrow(() -> new ResourceNotFoundException("Fee structure not found for class/year"));

        BigDecimal expected = structure.getTuitionFee()
                .add(nvl(structure.getTransportFee()))
                .add(nvl(structure.getExamFee()))
                .add(nvl(structure.getMiscFee()));

        return students.stream()
                .map(student -> {
                    FeePayment payment = feePaymentRepository.findByStudentIdAndFeeMonth(student.getId(), feeMonth).orElse(null);
                    BigDecimal paid = payment != null && payment.getStatus() == PaymentStatus.PAID ? payment.getAmount() : BigDecimal.ZERO;
                    BigDecimal pending = expected.subtract(paid);
                    return new FeeDefaulterDto(
                            student.getId(),
                            student.getFullName(),
                            student.getAdmissionNo(),
                            student.getClassName(),
                            feeMonth,
                            expected,
                            paid,
                            pending
                    );
                })
                .filter(item -> item.pendingAmount().compareTo(BigDecimal.ZERO) > 0)
                .toList();
    }

    private BigDecimal nvl(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private FeeStructureDto toDto(FeeStructure structure) {
        BigDecimal total = nvl(structure.getTuitionFee())
                .add(nvl(structure.getTransportFee()))
                .add(nvl(structure.getExamFee()))
                .add(nvl(structure.getMiscFee()));

        return new FeeStructureDto(
                structure.getId(),
                structure.getClassName(),
                structure.getAcademicYear(),
                structure.getTuitionFee(),
                structure.getTransportFee(),
                structure.getExamFee(),
                structure.getMiscFee(),
                total
        );
    }

    private FeePaymentDto toPaymentDto(FeePayment payment) {
        return new FeePaymentDto(
                payment.getId(),
                payment.getStudent().getId(),
                payment.getStudent().getFullName(),
                payment.getStudent().getAdmissionNo(),
                payment.getFeeMonth(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaymentDate(),
                payment.getPaymentMode(),
                payment.getTransactionReference(),
                payment.getReceipt() != null ? payment.getReceipt().getReceiptNo() : null,
                payment.getRemarks()
        );
    }
}
