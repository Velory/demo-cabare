package com.ua.cabare.validation;

import com.ua.cabare.domain.dto.PasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangePasswordValidator implements ConstraintValidator<MatchPassword, PasswordDto> {

  @Override
  public void initialize(MatchPassword constraintAnnotation) {

  }

  @Override
  public boolean isValid(PasswordDto passwordDto, ConstraintValidatorContext context) {
    return passwordDto.getNewPassword().equals(passwordDto.getMatchingPassword());
  }
}
