package com.ua.cabare.exceptions;

public class CardBlockedException extends RuntimeException {

  public CardBlockedException() {
    super("card is blocked");
  }
}
