package com.ua.cabare.controllers;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.PayStatus;
import com.ua.cabare.domain.bill.BillDto;
import com.ua.cabare.domain.bill.BillPrint;
import com.ua.cabare.domain.bill.OrderIn;
import com.ua.cabare.domain.bill.OrderPrint;
import com.ua.cabare.services.BillService;
import com.ua.cabare.services.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

  @Autowired
  private BillService billService;
  @Autowired
  private SecurityService securityService;

  public void setBillService(BillService billService) {
    this.billService = billService;
  }


  @RequestMapping(value = "/open", method = RequestMethod.PUT)
  public List<OrderPrint> openBill(@RequestBody BillDto billDto, @RequestParam Long employeeId) {
    securityService.authorizeEmployee(employeeId);
    return billService.openBill(billDto);
  }

  @RequestMapping(value = "/add/orderitems", method = RequestMethod.PUT)
  public List<OrderPrint> addOrder(@RequestParam long billId, @RequestBody List<OrderIn> orderIns,
      @RequestParam Long employeeId) {
    securityService.authorizeEmployee(employeeId);
    return billService.addOrders(billId, orderIns);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public void updateBill(@RequestParam long billId, @RequestBody BillDto billDto) {
    billService.update(billDto, billId);
  }

  @RequestMapping(value = "/add/payment", method = RequestMethod.PUT)
  public void addPayment(@RequestParam long billId, @RequestParam Money income) {
    billService.addPayment(billId, income);
  }

  @RequestMapping(value = "/preclose", method = RequestMethod.POST)
  public BillPrint preCloseBill(@RequestParam(name = "bill_id") Long billId,
      @RequestParam(name = "discount_id") Long discountId) {
    return billService.preCloseBill(billId, discountId);
  }

  @RequestMapping(value = "/close", method = RequestMethod.POST)
  public void close(@RequestParam(name = "bill_id") Long billId) {
    billService.close(billId);
  }

  @RequestMapping(value = "/opened", method = RequestMethod.POST)
  public List<BillPrint> getOpened(@RequestParam Long employeeId) {
    securityService.authorizeEmployee(employeeId);
    return billService.getOpened();
  }

  @RequestMapping(value = "/all/opened")
  public List<BillPrint> getOpenedAll() {
    return billService.getOpenedAll();
  }

  @RequestMapping(value = "/all/{paystatus}")
  public List<BillPrint> getBillsByPayStatus(
      @PathVariable(name = "paystatus") PayStatus payStatus) {
    return billService.getBillsByPayStatus(payStatus);
  }
}
