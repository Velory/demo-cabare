package com.ua.cabare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "ACCESS_DENIED")
public class AccessDeniedException extends RuntimeException {

}
