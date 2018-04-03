package com.ua.cabare.exceptions;

public class ActualCalculationNotFoundException extends RuntimeException {

  private static final String msg = "Actual calculation is not found for the dish:";

  public ActualCalculationNotFoundException(String message) {
    super(msg + message);
  }
}
