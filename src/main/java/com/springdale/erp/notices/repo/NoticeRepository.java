package com.springdale.erp.notices.repo;

import com.springdale.erp.notices.entity.Notice;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByActiveTrueAndPublishedDateLessThanEqualOrderByPublishedDateDesc(LocalDate date);
}
