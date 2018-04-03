package com.ua.cabare.controllers;


import com.ua.cabare.domain.GenericResponse;
import com.ua.cabare.models.ShiftTimetable;
import com.ua.cabare.services.ShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/shift")
public class EditShiftController {

  @Autowired
  private ShiftService shiftService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = "/current-shift", method = RequestMethod.GET)
  public GenericResponse loadShift() {
    shiftService.getShiftTimetable();
    return new GenericResponse("successfully", shiftService.getShiftTimetable());
  }

  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public GenericResponse saveNewShift(@RequestBody ShiftTimetable shiftTimetable,
      HttpServletRequest request) {
    shiftService.createShift(shiftTimetable);
    return new GenericResponse(messageSource.getMessage(
        "message.newShift", null, request.getLocale()));
  }

  @RequestMapping(value = "/edited", method = RequestMethod.POST)
  public GenericResponse updateShift(@RequestBody ShiftTimetable shiftTimetable,
      HttpServletRequest request) {
    shiftService.updateShift(shiftTimetable);
    return new GenericResponse(messageSource.getMessage(
        "message.updateShift", null, request.getLocale()));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public GenericResponse deleteShift(@PathVariable Long id, HttpServletRequest request) {
    shiftService.deleteShift(id);
    return new GenericResponse(messageSource.getMessage(
        "message.deleteShift", null, request.getLocale()));
  }
}
