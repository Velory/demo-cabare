package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "DISH_NOT_SPECIFIED")
public class DishNotSpecifiedException extends RuntimeException {

}
