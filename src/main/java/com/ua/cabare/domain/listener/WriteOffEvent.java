package com.ua.cabare.domain.listener;

import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Calculation;

import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.Map;

public class WriteOffEvent extends ApplicationEvent {

  private Long reasonId = 2L;
  private Map<Long, List<Calculation>> calculationMap;

  public WriteOffEvent(Bill bill, Map<Long, List<Calculation>> calculationMap) {
    super(bill);
    this.calculationMap = calculationMap;
  }

  public Long getReasonId() {
    return reasonId;
  }

  public Map<Long, List<Calculation>> getCalculationMap() {
    return calculationMap;
  }
}
