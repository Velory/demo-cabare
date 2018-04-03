package com.ua.cabare.domain;

import com.ua.cabare.models.Bill;

public class BillCashbackTuple {

  public final Bill bill;
  public final Money cashback;

  public BillCashbackTuple(Bill bill, Money cashback) {
    this.bill = bill;
    this.cashback = cashback;
  }
}
