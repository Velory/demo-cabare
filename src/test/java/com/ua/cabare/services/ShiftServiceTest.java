package com.ua.cabare.services;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Employee;
import com.ua.cabare.models.ShiftStatus;
import com.ua.cabare.models.ShiftTimetable;
import com.ua.cabare.repositories.BillRepository;
import com.ua.cabare.repositories.ShiftStatusRepository;
import com.ua.cabare.repositories.ShiftTimetableRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShiftServiceTest {

  private ShiftTimetable shiftTimetable;
  private ShiftTimetable shiftTimetable1;

  @MockBean
  private ShiftTimetableRepository timetableRepository;

  @MockBean
  private ShiftStatusRepository shiftStatusRepository;

  @MockBean
  private BillRepository billRepository;

  @MockBean
  private TimeService timeService;

  @Autowired
  private ShiftService shiftService;

  @Before
  public void setUp() throws Exception {

    shiftTimetable = new ShiftTimetable();
    shiftTimetable.setDayOfWeek(DayOfWeek.WEDNESDAY);
    shiftTimetable.setEmployee(new Employee());

    shiftTimetable1 = new ShiftTimetable();
    shiftTimetable1.setDayOfWeek(DayOfWeek.WEDNESDAY);
    shiftTimetable1.setEmployee(new Employee());

    List<ShiftTimetable> shiftTimetables = Arrays.asList(shiftTimetable, shiftTimetable1);

    when(timetableRepository.findAllByDayOfWeek(DayOfWeek.WEDNESDAY)).thenReturn(shiftTimetables);
    when(timeService.getCurrentDate()).thenReturn(LocalDate.of(2017, 11, 29));
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void shouldGetWednesdayShift() throws Exception {
    List<Employee> currentShift = shiftService.getCurrentShift();
    assertThat(shiftTimetable.getEmployee()).isSameAs(currentShift.get(0));
    assertThat(shiftTimetable1.getEmployee()).isSameAs(currentShift.get(1));
  }

  @Test(expected = RuntimeException.class)
  public void shouldThrowRuntimeExceptionWhenCreateNewShift() throws Exception {
    doThrow(new RuntimeException()).when(timetableRepository).saveAndFlush(any(ShiftTimetable.class));
    shiftService.createShift(new ShiftTimetable());
  }

  @Test(expected = RuntimeException.class)
  public void shouldThrowRuntimeExceptionWhenUpdateShift() throws Exception {
    doThrow(new RuntimeException()).when(timetableRepository).saveAndFlush(any(ShiftTimetable.class));
    shiftService.updateShift(new ShiftTimetable());
  }

  @Test(expected = RuntimeException.class)
  public void shouldDeleteTuesdayShift() throws Exception {
    doThrow(new RuntimeException()).when(timetableRepository).delete(anyLong());
    shiftService.deleteShift(1L);
  }

  @Test
  public void shouldCloseShift_WhenNoInteractionsRepositoryThenCorrect() throws Exception {
    shiftService.closeCash();
    shiftService.closeShift();
    verify(timetableRepository, never()).findAllByDayOfWeek(anyObject());
    shiftService.getCurrentShift();
  }

  @Test
  public void shouldOpenShift_WhenInteractionRepositoryThenCorrect() throws Exception {
    shiftService.openShift();
    verify(timetableRepository, times(1)).findAllByDayOfWeek(anyObject());
    List<Employee> currentShift = shiftService.getCurrentShift();
    assertThat(shiftTimetable.getEmployee()).isSameAs(currentShift.get(0));
  }

  @Test
  public void shouldCloseCash_WhenNoInteractionsBillAndInteractionTimetableRepositoryThenCorrect()
      throws Exception {
    ShiftStatus shiftStatus = new ShiftStatus();
    shiftStatus.setCashClosed(true);
    shiftStatus.setShiftClosed(false);
    when(shiftStatusRepository.findOne(anyLong())).thenReturn(shiftStatus);

    shiftService.closeCash();

    Bill bill = new Bill();
    verify(billRepository, never()).save(bill);

    List<Employee> currentShift = shiftService.getCurrentShift();
    verify(timetableRepository, times(1)).findAllByDayOfWeek(anyObject());
    assertThat(shiftTimetable.getEmployee()).isSameAs(currentShift.get(0));
  }

}