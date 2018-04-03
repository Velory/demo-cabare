package com.ua.cabare.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinDoubleValidator.class)
@Documented
public @interface MinDouble {

  String message() default "value should be greater";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  double value();
}
