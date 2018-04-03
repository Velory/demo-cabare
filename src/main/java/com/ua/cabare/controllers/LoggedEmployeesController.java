package com.ua.cabare.controllers;

import com.ua.cabare.domain.GenericResponse;
import com.ua.cabare.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoggedEmployeesController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping("/logged-employees")
  public GenericResponse getLoggedEmployees(HttpServletRequest request) {
    return new GenericResponse(messageSource.getMessage(
        "message.employeesFromSession", null, request.getLocale()),
        employeeService.getLoggedEmployeesFromSession());
  }

}
