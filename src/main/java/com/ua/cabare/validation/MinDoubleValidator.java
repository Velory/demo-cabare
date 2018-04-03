package com.ua.cabare.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinDoubleValidator implements ConstraintValidator<MinDouble, Double> {

  private double value;

  public void initialize(MinDouble constraint) {
    value = constraint.value();
  }

  public boolean isValid(Double obj, ConstraintValidatorContext context) {
    return obj != null && obj.compareTo(value) > 0;
  }
}
