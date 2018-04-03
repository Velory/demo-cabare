package com.ua.cabare.controllers;

import com.ua.cabare.domain.GenericResponse;
import com.ua.cabare.exceptions.EmployeeNotFoundException;
import com.ua.cabare.models.Employee;
import com.ua.cabare.domain.dto.PasswordDto;
import com.ua.cabare.exceptions.ChangePasswordException;
import com.ua.cabare.event.ResetPasswordEvent;
import com.ua.cabare.services.EmployeeService;
import com.ua.cabare.services.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class ChangePasswordController {

  @Autowired
  private PasswordService passwordService;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @RequestMapping(value = "/change", method = RequestMethod.POST)
  public GenericResponse changePassword(@RequestBody @Valid PasswordDto passwordDto,
      HttpServletRequest request) {
    if (passwordService.isOldPasswordValid(passwordDto)) {
      passwordService.createNewPassword(passwordDto);
      return new GenericResponse(messageSource.getMessage(
          "message.changePassword", null, request.getLocale()));
    }
    throw new ChangePasswordException();
  }

  @RequestMapping(value = "/reset", method = RequestMethod.POST)
  public GenericResponse resetPasswordIfForgot(HttpServletRequest request) {
    Employee employee = employeeService.getEmployeeByEmail(((Employee) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal())
        .getEmail());
    eventPublisher.publishEvent(new ResetPasswordEvent(employee,
        employeeService.getAppUrl(request), request.getLocale()));
    return new GenericResponse(messageSource.getMessage("message.resetPassword",
        null, request.getLocale()));
  }

  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  public GenericResponse updatePassword(@PathVariable Long id,
      @RequestBody @Valid PasswordDto passwordDto, HttpServletRequest request) {
    Employee employee = employeeService.getById(id);
    if (employee == null) throw new EmployeeNotFoundException();
    employee.setPassword(passwordDto.getNewPassword());
    employeeService.updateEmployee(employee);
    return new GenericResponse(messageSource.getMessage(
        "message.changePassword", null, request.getLocale()));
  }
}
