package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Incorrect format")
public class FormatException extends Exception {

  public FormatException() {
  }

  public FormatException(String message) {
    super(message);
  }
}
