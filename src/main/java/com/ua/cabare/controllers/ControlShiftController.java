package com.ua.cabare.controllers;

import com.ua.cabare.domain.GenericResponse;
import com.ua.cabare.models.Employee;
import com.ua.cabare.services.BillService;
import com.ua.cabare.services.ShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/control-shift")
public class ControlShiftController {

  @Autowired
  private ShiftService shiftService;
  
  @Autowired
  private BillService billService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = "/open", method = RequestMethod.GET)
  public GenericResponse openCurrentShift(HttpServletRequest request) {
    List<Employee> currentShift = shiftService.openShift();
    return new GenericResponse(messageSource.getMessage(
        "message.shiftOpened", null, request.getLocale()), currentShift);
  }

  @RequestMapping(value = "/close")
  public GenericResponse closeCurrentShift(HttpServletRequest request) {
    if (!shiftService.closeShift()) {
      return new GenericResponse(messageSource.getMessage(
          "message.cashNotClosed", null, request.getLocale()));
    }
    return new GenericResponse(messageSource.getMessage(
        "message.shiftClosed", null, request.getLocale()));
  }

  @RequestMapping(value = "/close-cash")
  public GenericResponse closeCurrentCash(HttpServletRequest request) {
    if (!shiftService.closeCash()) {
      return new GenericResponse(messageSource.getMessage(
          "message.cashNotClosed", null, request.getLocale()), billService.getOpenedAll());
    }
    return new GenericResponse(messageSource.getMessage(
        "message.cashClosed", null, request.getLocale()));
  }

}
