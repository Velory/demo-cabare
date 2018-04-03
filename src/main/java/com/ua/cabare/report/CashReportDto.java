package com.ua.cabare.report;

import com.ua.cabare.domain.PayType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CashReportDto implements Serializable {

  private LocalDateTime cashReportStartDate;
  private LocalDateTime cashReportEndDate;
  private PayType payType;
  private boolean numberOfTables;
  private boolean numberOfVisitors;
  private boolean averageAmount;

  public CashReportDto() {
  }

  public CashReportDto(LocalDateTime cashReportStartDate, LocalDateTime cashReportEndDate,
      PayType payType, boolean numberOfTables, boolean numberOfVisitors, boolean averageAmount) {
    this.cashReportStartDate = cashReportStartDate;
    this.cashReportEndDate = cashReportEndDate;
    this.payType = payType;
    this.numberOfTables = numberOfTables;
    this.numberOfVisitors = numberOfVisitors;
    this.averageAmount = averageAmount;
  }

  public LocalDateTime getCashReportStartDate() {
    return cashReportStartDate;
  }

  public LocalDateTime getCashReportEndDate() {
    return cashReportEndDate;
  }

  public PayType getPayType() {
    return payType;
  }

  public boolean isNumberOfTables() {
    return numberOfTables;
  }

  public boolean isNumberOfVisitors() {
    return numberOfVisitors;
  }

  public boolean isAverageAmount() {
    return averageAmount;
  }


}
