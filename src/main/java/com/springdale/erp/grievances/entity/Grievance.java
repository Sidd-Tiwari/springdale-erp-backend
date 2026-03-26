package com.springdale.erp.grievances.entity;

import com.springdale.erp.users.entity.User;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "grievances", indexes = {
        @Index(name = "idx_grievance_status", columnList = "status")
})
public class Grievance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_line", nullable = false, length = 150)
    private String subjectLine;

    @Column(name = "description_text", nullable = false, columnDefinition = "TEXT")
    private String descriptionText;

    @Column(nullable = false, length = 30)
    private String status = "OPEN";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raised_by_user_id", nullable = false)
    private User raisedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getSubjectLine() { return subjectLine; }
    public void setSubjectLine(String subjectLine) { this.subjectLine = subjectLine; }
    public String getDescriptionText() { return descriptionText; }
    public void setDescriptionText(String descriptionText) { this.descriptionText = descriptionText; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getRaisedBy() { return raisedBy; }
    public void setRaisedBy(User raisedBy) { this.raisedBy = raisedBy; }
}
