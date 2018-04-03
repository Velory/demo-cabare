package com.ua.cabare.validation;

import com.ua.cabare.domain.bill.DiscountDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class DiscountDtoValidator implements Validator {

  @Autowired
  private LocalValidatorFactoryBean validator;

  @Override
  public boolean supports(Class<?> clazz) {
    return DiscountDto.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.invokeValidator(validator, target, errors);
  }
}
