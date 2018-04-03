package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "DISCOUNT_CARD_NOT_FOUND")
public class DiscountCardNotFoundException extends RuntimeException {

}
