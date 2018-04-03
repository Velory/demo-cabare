package com.ua.cabare.exceptions;

public class EmployeeAlreadyExistException extends RuntimeException {

  public EmployeeAlreadyExistException() {
  }

  public EmployeeAlreadyExistException(String message) {
    super(message);
  }
}
