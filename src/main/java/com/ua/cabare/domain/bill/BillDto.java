package com.ua.cabare.domain.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.domain.PayType;
import com.ua.cabare.domain.SaleType;
import com.ua.cabare.models.Discount;

import java.util.List;

public class BillDto {

  @JsonProperty("table_number")
  private Integer tableNumber;

  @JsonProperty("number_of_persons")
  private Integer numberOfPersons;

  @JsonProperty("discount")
  private Discount discount;

  @JsonProperty("sale_type")
  private SaleType saleType;

  @JsonProperty("pay_type")
  private PayType payType;

  @JsonProperty("order_items")
  private List<OrderIn> orderIns;


  public Integer getTableNumber() {
    return tableNumber;
  }

  public void setTableNumber(Integer tableNumber) {
    this.tableNumber = tableNumber;
  }

  public Integer getNumberOfPersons() {
    return numberOfPersons;
  }

  public void setNumberOfPersons(Integer numberOfPersons) {
    this.numberOfPersons = numberOfPersons;
  }

  public Discount getDiscount() {
    return discount;
  }

  public void setDiscount(Discount discount) {
    this.discount = discount;
  }

  public SaleType getSaleType() {
    return saleType;
  }

  public void setSaleType(SaleType saleType) {
    this.saleType = saleType;
  }

  public PayType getPayType() {
    return payType;
  }

  public void setPayType(PayType payType) {
    this.payType = payType;
  }

  public List<OrderIn> getOrderIns() {
    return orderIns;
  }

  public void setOrderIns(List<OrderIn> orderIns) {
    this.orderIns = orderIns;
  }
}
