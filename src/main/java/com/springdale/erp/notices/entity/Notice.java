package com.springdale.erp.notices.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "notices", indexes = {
        @Index(name = "idx_notice_published", columnList = "published_date")
})
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notice_title", nullable = false, length = 150)
    private String noticeTitle;

    @Column(name = "notice_body", nullable = false, columnDefinition = "TEXT")
    private String noticeBody;

    @Column(name = "target_role", length = 20)
    private String targetRole;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getNoticeTitle() { return noticeTitle; }
    public void setNoticeTitle(String noticeTitle) { this.noticeTitle = noticeTitle; }
    public String getNoticeBody() { return noticeBody; }
    public void setNoticeBody(String noticeBody) { this.noticeBody = noticeBody; }
    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
