package com.ua.cabare.domain.dto;

public class LoggegEmployeesDto {

  private String name;

  public LoggegEmployeesDto(String name) {
    this.name = name;
  }

  public LoggegEmployeesDto() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
