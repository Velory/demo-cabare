package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "DISH_CATEGORY_NOT_FOUND")
public class DishCategoryNotFoundException extends RuntimeException {

}
