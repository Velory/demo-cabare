package com.ua.cabare.domain;

import com.ua.cabare.models.RawMaterial;

import java.time.LocalDate;
import java.util.Set;

public class Report {

  private LocalDate startPeriod;
  private LocalDate endPeriod;
  private Set<RawMaterial> ingridients;
}
