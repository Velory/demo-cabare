package com.ua.cabare.models;

import com.ua.cabare.domain.Money;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "order_number")
  private Long orderNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dish_id")
  private Dish dish;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "total_price")
  @Convert(converter = MoneyConverter.class)
  private Money totalPrice;

  @Column(name = "comments")
  private String comments;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bill_id")
  private Bill bill;

  public OrderItem() {
  }

  public OrderItem(Dish dish, Integer quantity, String comments, Long orderNumber) {
    this.dish = dish;
    this.quantity = quantity;
    this.totalPrice = dish.getPrice().multiply(quantity);
    this.comments = comments;
    this.orderNumber = orderNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Long orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Money getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Money totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Dish getDish() {
    return dish;
  }

  public void setDish(Dish dish) {
    this.dish = dish;
  }

  public Bill getBill() {
    return bill;
  }

  public void setBill(Bill bill) {
    this.bill = bill;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(orderNumber, orderItem.orderNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderNumber);
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "id=" + id +
        ", orderNumber=" + orderNumber +
        ", dish=" + dish +
        ", quantity=" + quantity +
        '}';
  }
}
