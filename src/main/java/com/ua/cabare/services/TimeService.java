package com.ua.cabare.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TimeService {

  public LocalDateTime getCurrentTime() {
    return LocalDateTime.now();
  }

  public LocalDate getCurrentDate() {
    return LocalDate.now();
  }
}
