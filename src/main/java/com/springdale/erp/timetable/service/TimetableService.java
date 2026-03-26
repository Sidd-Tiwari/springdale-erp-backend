package com.springdale.erp.timetable.service;

import com.springdale.erp.common.exception.ResourceNotFoundException;
import com.springdale.erp.timetable.dto.TimetableEntryDto;
import com.springdale.erp.timetable.entity.TimetableEntry;
import com.springdale.erp.timetable.repo.TimetableRepository;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TimetableService {

    private final TimetableRepository timetableRepository;

    public TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    public TimetableEntryDto save(TimetableEntryDto request) {
        TimetableEntry entry = new TimetableEntry();
        entry.setClassName(request.className());
        entry.setSection(request.section());
        entry.setDayOfWeek(request.dayOfWeek());
        entry.setSubjectName(request.subjectName());
        entry.setFacultyName(request.facultyName());
        entry.setStartTime(request.startTime());
        entry.setEndTime(request.endTime());
        return toDto(timetableRepository.save(entry));
    }

    @Transactional(readOnly = true)
    public List<TimetableEntryDto> getByClassSection(String className, String section) {
        return timetableRepository.findByClassNameAndSectionOrderByDayOfWeekAscStartTimeAsc(className, section)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<TimetableEntryDto> getByClassSectionDay(String className, String section, DayOfWeek dayOfWeek) {
        return timetableRepository.findByClassNameAndSectionAndDayOfWeekOrderByStartTimeAsc(className, section, dayOfWeek)
                .stream().map(this::toDto).toList();
    }

    public void delete(Long id) {
        if (!timetableRepository.existsById(id)) {
            throw new ResourceNotFoundException("Timetable entry not found with id: " + id);
        }
        timetableRepository.deleteById(id);
    }

    private TimetableEntryDto toDto(TimetableEntry entry) {
        return new TimetableEntryDto(
                entry.getId(),
                entry.getClassName(),
                entry.getSection(),
                entry.getDayOfWeek(),
                entry.getSubjectName(),
                entry.getFacultyName(),
                entry.getStartTime(),
                entry.getEndTime()
        );
    }
}
