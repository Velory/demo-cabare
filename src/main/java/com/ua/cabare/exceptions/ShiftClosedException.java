package com.ua.cabare.exceptions;

public class ShiftClosedException extends RuntimeException {

  public ShiftClosedException() {
    super();
  }

  public ShiftClosedException(String message) {
    super(message);
  }
}
