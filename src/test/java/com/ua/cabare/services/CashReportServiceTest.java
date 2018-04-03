package com.ua.cabare.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.PayType;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.OrderItem;
import com.ua.cabare.report.CashReport;
import com.ua.cabare.report.CashReportDto;
import com.ua.cabare.repositories.BillRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CashReportServiceTest {

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private List<Bill> bills;
  private CashReportDto cashReportDto;

  @Autowired
  private CashReportService cashReportService;

  @MockBean
  private BillRepository billRepository;

  @Before
  public void setUp() throws Exception {

    Bill bill1 = new Bill();
    bill1.setOpenBillTime(LocalDateTime.parse("2017-11-01T00:00"));
    bill1.setNumberOfPersons(2);
    bill1.setTableNumber(5);
    bill1.setPayType(PayType.CASH);

    Bill bill2 = new Bill();
    bill2.setOpenBillTime(LocalDateTime.parse("2017-10-01T00:00"));
    bill2.setNumberOfPersons(3);
    bill2.setTableNumber(2);
    bill2.setPayType(PayType.CASH);

    OrderItem orderItem1 = new OrderItem();
    orderItem1.setTotalPrice(Money.valueOf(85070));
    OrderItem orderItem2 = new OrderItem();
    orderItem2.setTotalPrice(Money.valueOf(73033));

    OrderItem orderItem3 = new OrderItem();
    orderItem3.setTotalPrice(Money.valueOf(20078));
    OrderItem orderItem4 = new OrderItem();
    orderItem4.setTotalPrice(Money.valueOf(43201));

    Arrays.asList(orderItem1, orderItem2).forEach(bill1::addOrderItem);
    Arrays.asList(orderItem3, orderItem4).forEach(bill2::addOrderItem);

    bills = Arrays.asList(bill1, bill2);

    startDate = LocalDateTime.parse("2017-11-01T00:00");
    endDate = LocalDateTime.parse("2017-10-01T00:00");

    cashReportDto = new CashReportDto(LocalDateTime.parse("2017-11-01T00:00"),
            LocalDateTime.parse("2017-10-01T00:00"),
        PayType.CASH, true, true,true);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void shouldCreateCashReport() throws Exception {

    when(billRepository.findByPayType(
        startDate, endDate, PayType.CASH)).thenReturn(bills);
    CashReport cashReport = cashReportService.createCashReport(cashReportDto);
    assertNotNull(cashReport);

    assertEquals(5, cashReport.getSummaryNumberOfVisitors());
    assertEquals(2, cashReport.getSummaryNumberOfTables());
    assertEquals(110691, cashReport.getAverageAmount());
    assertEquals(63279, cashReport.getMinBill());
    assertEquals(158103, cashReport.getMaxBill());
  }

}