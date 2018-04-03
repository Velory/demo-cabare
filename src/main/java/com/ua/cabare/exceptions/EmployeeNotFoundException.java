package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "EMPLOYEE_NOT_FOUND")
public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException() {
  }

  public EmployeeNotFoundException(String message) {
    super(message);
  }
}
