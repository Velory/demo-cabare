package com.ua.cabare.exceptions;

public class TokenNotFoundException extends RuntimeException {

  public TokenNotFoundException() {
    super();
  }

  public TokenNotFoundException(String message) {
    super(message);
  }
}
