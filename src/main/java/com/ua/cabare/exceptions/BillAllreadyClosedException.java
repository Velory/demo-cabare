package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "BILL_AllREADY_CLOSED")
public class BillAllreadyClosedException extends RuntimeException {

}
