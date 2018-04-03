package com.ua.cabare.exceptions;

public class ChangePasswordException extends RuntimeException {

  public ChangePasswordException() {
  }

  public ChangePasswordException(String message) {
    super(message);
  }
}
