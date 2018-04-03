package com.ua.cabare.report;

import com.ua.cabare.models.Bill;

import java.io.Serializable;
import java.util.List;

public class CashReport implements Serializable {

  private List<Bill> cashReportBills;
  private int summaryNumberOfTables;
  private int summaryNumberOfVisitors;
  private long averageAmount;
  private long minBill;
  private long maxBill;

  public CashReport() {
  }

  public CashReport(List<Bill> cashReportBills, int summaryNumberOfTables,
      int summaryNumberOfVisitors, long averageAmount) {
    this.cashReportBills = cashReportBills;
    this.summaryNumberOfTables = summaryNumberOfTables;
    this.summaryNumberOfVisitors = summaryNumberOfVisitors;
    this.averageAmount = averageAmount;
  }

  public List<Bill> getCashReportBills() {
    return cashReportBills;
  }

  public void setCashReportBills(List<Bill> cashReportBills) {
    this.cashReportBills = cashReportBills;
  }

  public int getSummaryNumberOfTables() {
    return summaryNumberOfTables;
  }

  public void setSummaryNumberOfTables(int summaryNumberOfTables) {
    this.summaryNumberOfTables = summaryNumberOfTables;
  }

  public int getSummaryNumberOfVisitors() {
    return summaryNumberOfVisitors;
  }

  public void setSummaryNumberOfVisitors(int summaryNumberOfVisitors) {
    this.summaryNumberOfVisitors = summaryNumberOfVisitors;
  }

  public long getAverageAmount() {
    return averageAmount;
  }

  public void setAverageAmount(long averageAmount) {
    this.averageAmount = averageAmount;
  }

  public long getMinBill() {
    return minBill;
  }

  public void setMinBill(long minBill) {
    this.minBill = minBill;
  }

  public long getMaxBill() {
    return maxBill;
  }

  public void setMaxBill(long maxBill) {
    this.maxBill = maxBill;
  }
}
