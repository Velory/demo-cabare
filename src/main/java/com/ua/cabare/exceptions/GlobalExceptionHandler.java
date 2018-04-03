package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Map handle(ConstraintViolationException ex) {
    ex.printStackTrace();
    return error(ex.getConstraintViolations().stream()
        .map(er -> er.getMessage())
        .collect(Collectors.toList()));
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Map handle(RuntimeException ex) {
    ex.printStackTrace();
    return error(ex.getMessage());
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Map handle(MethodArgumentNotValidException ex) {
    ex.printStackTrace();
    return error(ex.getBindingResult().getAllErrors().stream()
        .map(er -> er.getDefaultMessage())
        .collect(Collectors.toList()));
  }

  private Map error(Object message) {
    return Collections.singletonMap("error", message);
  }
}
