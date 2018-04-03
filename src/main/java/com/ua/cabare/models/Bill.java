package com.ua.cabare.models;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.PayStatus;
import com.ua.cabare.domain.PayType;
import com.ua.cabare.domain.SaleType;
import com.ua.cabare.domain.bill.BillDto;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bill")
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "bill_number")
  private Integer billNumber;

  @Column(name = "open_bill_time", columnDefinition = "datetime")
  private LocalDateTime openBillTime;

  @Column(name = "close_bill_time", columnDefinition = "datetime")
  private LocalDateTime closeBillTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Column(name = "table_number")
  private Integer tableNumber;

  @Column(name = "number_of_persons")
  private Integer numberOfPersons;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id", nullable = true)
  private Discount discount;

  @Column(name = "sale_type_id")
  @Enumerated(EnumType.ORDINAL)
  private SaleType saleType;

  @Column(name = "pay_type_id")
  @Enumerated(EnumType.ORDINAL)
  private PayType payType;

  @Column(name = "pay_status_id")
  @Enumerated(EnumType.ORDINAL)
  private PayStatus payStatus;

  @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @Convert(converter = MoneyConverter.class)
  @Column(name = "money_paid")
  private Money moneyPaid = Money.ZERO;

  @Column(name = "is_opened", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
  private Boolean opened = true;

  @Column(name = "is_active_shift", columnDefinition = "BIT(1) DEFAULT 1")
  private Boolean activeShift = true;

  public Bill() {
  }

  public Bill(BillDto billDto, List<OrderItem> orderItem) {
    this();
    this.discount = billDto.getDiscount();
    this.numberOfPersons = billDto.getNumberOfPersons();
    this.payType = billDto.getPayType();
    this.saleType = billDto.getSaleType();
    this.tableNumber = billDto.getTableNumber();
    orderItem.forEach(this::addOrderItem);
  }

  public void addOrderItem(OrderItem orderItem) {
    this.orderItems.add(orderItem);
    orderItem.setBill(this);
  }

  public void removeOrderItem(OrderItem orderItem) {
    this.orderItems.remove(orderItem);
    orderItem.setBill(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getBillNumber() {
    return billNumber;
  }

  public void setBillNumber(Integer billNumber) {
    this.billNumber = billNumber;
  }

  public LocalDateTime getOpenBillTime() {
    return openBillTime;
  }

  public void setOpenBillTime(LocalDateTime openBillTime) {
    this.openBillTime = openBillTime;
  }

  public LocalDateTime getCloseBillTime() {
    return closeBillTime;
  }

  public void setCloseBillTime(LocalDateTime closeBillTime) {
    this.closeBillTime = closeBillTime;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

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

  public PayStatus getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(PayStatus payStatus) {
    this.payStatus = payStatus;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public Money getMoneyPaid() {
    return moneyPaid;
  }

  public void setMoneyPaid(Money moneyPaid) {
    this.moneyPaid = moneyPaid;
  }

  public Boolean getOpened() {
    return opened;
  }

  public void setOpened(Boolean opened) {
    this.opened = opened;
  }

  public Boolean getActiveShift() {
    return activeShift;
  }

  public void setActiveShift(Boolean activeShift) {
    this.activeShift = activeShift;
  }

  public Money getBillPrice() {
    Money cost = Money.ZERO;
    for (OrderItem orderItem : this.getOrderItems()) {
      cost = cost.add(orderItem.getTotalPrice());
    }
    return discount != null
        ? cost.multiply(discount.getSize() / 100f)
        : cost;
  }

  public void addPayment(Money payment) {
    this.moneyPaid = this.moneyPaid.add(payment);
  }
}
