package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "stock wasn't specified", code = HttpStatus.BAD_REQUEST)
public class StockNotSpecifiedException extends RuntimeException {

}
