package com.springdale.erp.exams.entity;

import com.springdale.erp.students.entity.Student;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "exam_results", uniqueConstraints = {
        @UniqueConstraint(name = "uk_exam_result_exam_student_subject", columnNames = {"exam_id", "student_id", "subject_name"})
})
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "subject_name", nullable = false, length = 100)
    private String subjectName;

    @Column(name = "max_marks", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxMarks;

    @Column(name = "obtained_marks", nullable = false, precision = 10, scale = 2)
    private BigDecimal obtainedMarks;

    public Long getId() { return id; }
    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public BigDecimal getMaxMarks() { return maxMarks; }
    public void setMaxMarks(BigDecimal maxMarks) { this.maxMarks = maxMarks; }
    public BigDecimal getObtainedMarks() { return obtainedMarks; }
    public void setObtainedMarks(BigDecimal obtainedMarks) { this.obtainedMarks = obtainedMarks; }
}
