package com.ua.cabare.event;

import com.ua.cabare.models.Employee;

import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class ResetPasswordEvent extends ApplicationEvent {

  private String appUrl;
  private Locale locale;
  private Employee employee;

  public ResetPasswordEvent(Employee employee, String appUrl, Locale locale) {
    super(employee);
    this.appUrl = appUrl;
    this.locale = locale;
    this.employee = employee;
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
}
