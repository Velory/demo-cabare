package com.ua.cabare.exceptions;

public class ActualCalculationExistException extends RuntimeException {

  public ActualCalculationExistException() {
    super("Calculation for the dish is already exist. Just update it");
  }
}
