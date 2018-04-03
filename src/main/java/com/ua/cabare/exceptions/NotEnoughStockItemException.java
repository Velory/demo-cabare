package com.ua.cabare.exceptions;

public class NotEnoughStockItemException extends RuntimeException {

  public NotEnoughStockItemException() {
  }

  public NotEnoughStockItemException(String message) {
    super(message);
  }
}
