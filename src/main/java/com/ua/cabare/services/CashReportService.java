package com.ua.cabare.services;

import com.ua.cabare.domain.Money;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.OrderItem;
import com.ua.cabare.report.CashReport;
import com.ua.cabare.report.CashReportDto;
import com.ua.cabare.repositories.BillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class CashReportService {

  private List<Bill> bills;

  @Autowired
  private BillRepository billRepository;

  public CashReport createCashReport(CashReportDto cashReportDto) {
    bills = billRepository.findByPayType(
        cashReportDto.getCashReportStartDate(),
        cashReportDto.getCashReportEndDate(),
        cashReportDto.getPayType());

    CashReport cashReport = generateCashReport(cashReportDto);

    return cashReport;
  }

  private CashReport generateCashReport(CashReportDto cashReportDto) {
    CashReport cashReport = new CashReport();

    if (cashReportDto.isNumberOfTables()) {
      cashReport.setSummaryNumberOfTables(countSummaryNumberOfTables());
    }

    if (cashReportDto.isNumberOfVisitors()) {
      cashReport.setSummaryNumberOfVisitors(countNumberOfVisitors());
    }

    if (cashReportDto.isAverageAmount()) {
      cashReport.setAverageAmount(countAverageAmount());
    }

    cashReport.setCashReportBills(bills);
    cashReport.setMinBill(getMinBill());
    cashReport.setMaxBill(getMaxBill());

    return cashReport;
  }

  private int countSummaryNumberOfTables() {
    return bills.size();
  }

  private int countNumberOfVisitors() {
    long count = bills.stream().mapToInt(Bill::getNumberOfPersons).sum();
    return (int) count;
  }

  private long countAverageAmount() {
    return (long) bills.stream()
            .mapToLong(bill -> bill.getBillPrice()
                    .getRawValue())
            .average()
            .getAsDouble();
  }

  private long getMinBill() {
    Bill bill = bills.stream().min(Comparator.comparingLong(b -> b.getBillPrice().getRawValue())).get();
    return bill.getBillPrice().getRawValue();
  }

  private long getMaxBill() {
    Bill bill =bills.stream().max(Comparator.comparingLong(b -> b.getBillPrice().getRawValue())).get();
    return bill.getBillPrice().getRawValue();
  }
}
