package com.ua.cabare.event;

import com.ua.cabare.models.Employee;
import com.ua.cabare.models.VerificationToken;

import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class ConfirmEmailEvent extends ApplicationEvent {

  private String appUrl;
  private Locale locale;
  private Employee employee;
  private VerificationToken existingToken;

  public ConfirmEmailEvent(Employee employee, Locale locale, String appUrl, VerificationToken existingToken) {
    super(employee);
    this.appUrl = appUrl;
    this.employee = employee;
    this.locale = locale;
    this.existingToken = existingToken;
  }

  public String getAppUrl() {
    return appUrl;
  }

  public Locale getLocale() {
    return locale;
  }

  public Employee getEmployee() {
    return employee;
  }

  public VerificationToken getExistingToken() {
    return existingToken;
  }
}
