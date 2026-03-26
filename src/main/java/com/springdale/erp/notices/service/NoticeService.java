package com.springdale.erp.notices.service;

import com.springdale.erp.common.exception.ResourceNotFoundException;
import com.springdale.erp.notices.dto.NoticeDto;
import com.springdale.erp.notices.entity.Notice;
import com.springdale.erp.notices.repo.NoticeRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeDto save(NoticeDto request) {
        Notice notice = new Notice();
        notice.setNoticeTitle(request.noticeTitle());
        notice.setNoticeBody(request.noticeBody());
        notice.setTargetRole(request.targetRole());
        notice.setPublishedDate(request.publishedDate());
        notice.setActive(request.active());
        return toDto(noticeRepository.save(notice));
    }

    @Transactional(readOnly = true)
    public List<NoticeDto> getAll() {
        return noticeRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<NoticeDto> getPublicNotices() {
        return noticeRepository.findByActiveTrueAndPublishedDateLessThanEqualOrderByPublishedDateDesc(LocalDate.now())
                .stream().map(this::toDto).toList();
    }

    public void delete(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notice not found with id: " + id);
        }
        noticeRepository.deleteById(id);
    }

    private NoticeDto toDto(Notice notice) {
        return new NoticeDto(
                notice.getId(),
                notice.getNoticeTitle(),
                notice.getNoticeBody(),
                notice.getTargetRole(),
                notice.getPublishedDate(),
                notice.isActive()
        );
    }
}
