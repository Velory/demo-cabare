package com.ua.cabare.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveImpl implements ConstraintValidator<Positive, String> {

  private final String pattern = "^\\d+(\\.\\d+)?$";

  @Override

  public void initialize(Positive constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null && !value.isEmpty() && value.matches(pattern);
  }
}
