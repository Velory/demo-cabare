package com.ua.cabare.controllers;

import com.ua.cabare.domain.GenericResponse;
import com.ua.cabare.models.Employee;
import com.ua.cabare.domain.dto.EmployeeDto;
import com.ua.cabare.event.ConfirmEmailEvent;
import com.ua.cabare.models.VerificationToken;
import com.ua.cabare.services.EmployeeService;
import com.ua.cabare.services.VerificationTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private VerificationTokenService verificationTokenService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @RequestMapping(value = "/employee", method = RequestMethod.POST)
  @ResponseBody
  public GenericResponse registrationNewEmployee(@RequestBody @Valid EmployeeDto employeeDto,
      HttpServletRequest request) {
    log.debug("Registering employee account with: " + employeeDto);
    Employee employee = employeeService.registerNewEmployeeAccount(employeeDto);

    return new GenericResponse(messageSource
        .getMessage("message.regSuccess", null, request.getLocale()));
  }

  @RequestMapping(value = "/event", method = RequestMethod.GET)
  @ResponseBody
  public GenericResponse registrationConfirm(@RequestParam String token, HttpServletRequest request)
      throws UnsupportedEncodingException {
    String result = verificationTokenService.validateVerificationToken(token);
    if (result.equals("valid")) {
      Employee employee = employeeService.getEmployee(token);
      if (employee != null) {
        return new GenericResponse(messageSource.getMessage("message.accountVerified",
            null, request.getLocale()));
      }
    }
    return new GenericResponse(messageSource.getMessage("auth.message." + result,
        null, request.getLocale()));
  }

  @RequestMapping(value = "/newRegistrationToken", method = RequestMethod.GET)
  public GenericResponse resendRegistrationToken(@RequestParam("token") String existingToken,
      HttpServletRequest request) {
    VerificationToken newToken = verificationTokenService.generateNewVerificationToken(existingToken);
    Employee employee = employeeService.getEmployee(newToken.getToken());
    eventPublisher.publishEvent(new ConfirmEmailEvent(employee, request.getLocale(),
        employeeService.getAppUrl(request), newToken));
    return new GenericResponse(messageSource.getMessage("message.resendToken", null,
        request.getLocale()));
  }

}
