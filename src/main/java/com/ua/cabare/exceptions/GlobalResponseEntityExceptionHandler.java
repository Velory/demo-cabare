package com.ua.cabare.exceptions;

import com.ua.cabare.domain.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  public GlobalResponseEntityExceptionHandler() {
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    logger.error("400 Status Code ", ex);
    BindingResult result = ex.getBindingResult();
    GenericResponse response = new GenericResponse(result.getAllErrors(),
        "Invalid" + result.getObjectName());
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    logger.error("400 Status Code ", ex);
    BindingResult result = ex.getBindingResult();
    GenericResponse response = new GenericResponse(result.getAllErrors(),
        "Invalid" + result.getObjectName());
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler({CashReportException.class})
  public ResponseEntity<Object> handleCashReportErrors(RuntimeException ex, WebRequest request) {
    logger.error("400 Status Code", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.cashReportError", null, request.getLocale()),
        "CreationCashReportFailed");
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({TokenNotFoundException.class})
  public ResponseEntity<Object> handleTokenErrors(RuntimeException ex, WebRequest request) {
    logger.error("401 Status Code", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.tokenNotFound", null, request.getLocale()),
        "TokenNotFound");
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<Object> handleAuthenticationFailure(RuntimeException ex,
      WebRequest request) {
    logger.error("403 Status Code ", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("auth.message.authError",
            null, request.getLocale()), "AuthenticationFailed");
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
  }

  @ExceptionHandler({EmployeeNotFoundException.class})
  public ResponseEntity<Object> handleEmployeeNotFound(RuntimeException ex, WebRequest request) {
    logger.error("404 Status Code ", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.employeeNotFound",
            null, request.getLocale()), "EmployeeNotFound");
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({EmployeeAlreadyExistException.class})
  public ResponseEntity<Object> handleEmployeeAlreadyExist(RuntimeException ex,
      WebRequest request) {
    logger.error("409 Status Code ", ex);
    GenericResponse response = new GenericResponse(messageSource.getMessage("message.regError",
        null, request.getLocale()), "EmployeeAlreadyExist");
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler({MailAuthenticationException.class})
  public ResponseEntity<Object> handleMail(RuntimeException ex, WebRequest request) {
    logger.error("500 Status Code ", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.email.config.error",
            null, request.getLocale()));
    return handleExceptionInternal(ex, response, new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler({ShiftSaveException.class})
  public ResponseEntity<Object> handleSaveShift(RuntimeException ex, WebRequest request) {
    logger.error("400 Status Code", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.shiftSaveError", null, request.getLocale()));
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler({ShiftLoadingException.class})
  public ResponseEntity<Object> handleLoadShift(RuntimeException ex, WebRequest request) {
    logger.error("400 Status Code", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.shiftError", null, request.getLocale()));
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler({ShiftClosedException.class})
  public ResponseEntity<Object> handleOpenShift(RuntimeException ex, WebRequest request) {
    logger.error("400 Status Code", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.shiftClosed", null, request.getLocale()));
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler({CashClosedException.class})
  public ResponseEntity<Object> handleCloseCash(RuntimeException ex, WebRequest request) {
    logger.error("400 Status Code", ex);
    GenericResponse response = new GenericResponse(
        messageSource.getMessage("message.cashClosed", null, request.getLocale()));
    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

}
