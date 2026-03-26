package com.springdale.erp.timetable.repo;

import com.springdale.erp.timetable.entity.TimetableEntry;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<TimetableEntry, Long> {
    List<TimetableEntry> findByClassNameAndSectionOrderByDayOfWeekAscStartTimeAsc(String className, String section);
    List<TimetableEntry> findByClassNameAndSectionAndDayOfWeekOrderByStartTimeAsc(String className, String section, DayOfWeek dayOfWeek);
}
