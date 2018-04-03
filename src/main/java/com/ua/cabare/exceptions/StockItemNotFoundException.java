package com.ua.cabare.exceptions;

public class StockItemNotFoundException extends RuntimeException {

  public StockItemNotFoundException() {
  }

  public StockItemNotFoundException(String message) {
    super(message);
  }
}
