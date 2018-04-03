package com.ua.cabare.domain;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class GenericResponse implements Serializable {

  private String message;
  private String error;
  private Object object;

  public GenericResponse(String message) {
    this.message = message;
  }

  public GenericResponse(String message, Object object) {
    this.message = message;
    this.object = object;
  }

  public GenericResponse(String message, String error) {
    this.message = message;
    this.error = error;
  }

  public GenericResponse(List<ObjectError> allErrors, String error) {
    this.error = error;
    this.message = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(
            Collectors.joining(","));
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }
}
