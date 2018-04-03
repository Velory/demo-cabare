package com.ua.cabare.services;

import com.ua.cabare.exceptions.ShiftLoadingException;
import com.ua.cabare.exceptions.ShiftSaveException;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Employee;
import com.ua.cabare.models.ShiftTimetable;
import com.ua.cabare.repositories.BillRepository;
import com.ua.cabare.repositories.ShiftTimetableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftService {

  @Autowired
  private ShiftTimetableRepository timetableRepository;

  @Autowired
  private ShiftStatusService shiftStatusService;

  @Autowired
  private BillRepository billRepository;

  @Autowired
  private TimeService timeService;

  public List<Employee> getCurrentShift() {
    DayOfWeek currentDay = timeService.getCurrentDate().getDayOfWeek();
    List<ShiftTimetable> allByDayOfWeek = timetableRepository.findAllByDayOfWeek(currentDay);
    List<Employee> employees = allByDayOfWeek.stream().map(ShiftTimetable::getEmployee)
        .collect(Collectors.toList());
    if (employees == null) {
      throw new ShiftLoadingException();
    }
    return employees;
  }

  public List<ShiftTimetable> getShiftTimetable() {
    return timetableRepository.findAll();
  }

  @Transactional
  public void createShift(ShiftTimetable shiftTimetable) {
    timetableRepository.saveAndFlush(shiftTimetable);
  }

  @Transactional
  public void updateShift(ShiftTimetable shiftTimetable) {
    ShiftTimetable shiftTimetableFromDb = timetableRepository.findOne(shiftTimetable.getId());
    if (shiftTimetableFromDb != null) {
      shiftTimetableFromDb.setDayOfWeek(shiftTimetable.getDayOfWeek());
      shiftTimetableFromDb.setEmployee(shiftTimetable.getEmployee());
      timetableRepository.saveAndFlush(shiftTimetableFromDb);
    } else {
      throw new ShiftSaveException();
    }
  }

  @Transactional
  public void deleteShift(Long id) {
    timetableRepository.delete(id);
  }

  public boolean closeShift() {
    if (shiftStatusService.find() == null) return false;
    if (shiftStatusService.find().isCashClosed()) {
      shiftStatusService.saveShiftStatus(true);
      return true;
    }
    return false;
  }

  public List<Employee> openShift() {
    shiftStatusService.saveCashStatus(false);
    shiftStatusService.saveShiftStatus(false);
    return getCurrentShift();
  }

  public boolean closeCash() {
    List<Bill> bills = billRepository.findAllByOpened(true);
    if (bills.isEmpty()) {
      shiftStatusService.saveCashStatus(true);
      return true;
    }
    return false;
  }
}
