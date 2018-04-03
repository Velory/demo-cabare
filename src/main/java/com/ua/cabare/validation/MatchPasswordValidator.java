package com.ua.cabare.validation;

import com.ua.cabare.domain.dto.EmployeeDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, EmployeeDto> {

  @Override
  public void initialize(MatchPassword constraintAnnotation) {
  }

  @Override
  public boolean isValid(EmployeeDto employeeDto, ConstraintValidatorContext context) {
    return employeeDto.getPassword().equals(employeeDto.getMatchingPassword());
  }
}
