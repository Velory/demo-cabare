package com.ua.cabare.domain.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.models.OrderItem;

public class OrderPrint {

  @JsonProperty("category")
  private String categoryName;

  @JsonProperty("dish_name")
  private String dishName;

  @JsonProperty("quantity")
  private Integer quantity;

  @JsonProperty("total_price")
  private String totalPrice;

  @JsonProperty("order_number")
  private Long orderNumber;

  @JsonProperty("comments")
  private String comments;

  public OrderPrint(OrderItem orderItem) {
    this.dishName = orderItem.getDish().getName();
    this.quantity = orderItem.getQuantity();
    this.totalPrice = orderItem.getTotalPrice().getValue();
    this.orderNumber = orderItem.getOrderNumber();
    this.comments = orderItem.getComments();
    this.categoryName = orderItem.getDish().getDishCategory().getName();
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getDishName() {
    return dishName;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public String getTotalPrice() {
    return totalPrice;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public String getComments() {
    return comments;
  }
}
