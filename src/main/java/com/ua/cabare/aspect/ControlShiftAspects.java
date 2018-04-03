package com.ua.cabare.aspect;

import com.ua.cabare.exceptions.CashClosedException;
import com.ua.cabare.exceptions.ShiftClosedException;
import com.ua.cabare.services.ShiftStatusService;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControlShiftAspects {

  @Autowired
  private ShiftStatusService shiftStatusService;

  @Before(value = "bean(*Controller) && !bean(controlShiftController)")
  public void controlShift() {
    if (shiftStatusService.find() == null) return;
    if (shiftStatusService.find().isShiftClosed()) {
      throw new ShiftClosedException();
    }
  }

  @Before(value = "execution(* com.ua.cabare.controllers.BillController.*(..))")
  public void controlCash() {
    if (shiftStatusService.find() == null) return;
    if (shiftStatusService.find().isCashClosed()) {
      throw new CashClosedException();
    }
  }

}
