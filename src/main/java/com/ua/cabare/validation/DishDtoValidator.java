package com.ua.cabare.validation;

import com.ua.cabare.domain.dish.DishDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
public class DishDtoValidator implements Validator {

  private Validator validator;

  @Autowired
  public void setValidator(LocalValidatorFactoryBean validator) {
    this.validator = validator;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return DishDto.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.invokeValidator(validator, target, errors);
  }
}
