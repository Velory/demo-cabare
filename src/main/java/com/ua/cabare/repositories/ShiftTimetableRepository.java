package com.ua.cabare.repositories;


import com.ua.cabare.models.ShiftTimetable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface ShiftTimetableRepository extends JpaRepository<ShiftTimetable, Long> {

  List<ShiftTimetable> findAllByDayOfWeek(DayOfWeek day);
}
