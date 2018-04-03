package com.ua.cabare.validation;

import com.google.common.base.Joiner;

import org.passay.AlphabeticalSequenceRule;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.NumericalSequenceRule;
import org.passay.PasswordData;
import org.passay.QwertySequenceRule;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  @Override
  public void initialize(ValidPassword constraintAnnotation) {

  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.asList(
        new LengthRule(4, 30),
        //new UppercaseCharacterRule(1),
        new DigitCharacterRule(1),
        //new SpecialCharacterRule(1),
        new NumericalSequenceRule(3, false),
        new AlphabeticalSequenceRule(3, false),
        new QwertySequenceRule(3, false),
        new WhitespaceRule()));
    RuleResult ruleResult = validator.validate(new PasswordData(password));
    if (ruleResult.isValid()) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(Joiner.on(", ")
        .join(validator.getMessages(ruleResult))).addConstraintViolation();
    return false;
  }
}
