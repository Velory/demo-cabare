package com.ua.cabare.exceptions;

public class CashClosedException extends RuntimeException {

  public CashClosedException() {
    super();
  }

  public CashClosedException(String message) {
    super(message);
  }
}
