package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "stock wasn't found", code = HttpStatus.BAD_REQUEST)
public class StockNotFoundException extends RuntimeException {

}
