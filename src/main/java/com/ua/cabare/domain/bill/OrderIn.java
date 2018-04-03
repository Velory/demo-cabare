package com.ua.cabare.domain.bill;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderIn {

  @JsonProperty("dish_id")
  private Long dishId;

  @JsonProperty("quantity")
  private Integer quantity;

  @JsonProperty("comments")
  private String comments;


  public Long getDishId() {
    return dishId;
  }

  public void setDishId(Long dishId) {
    this.dishId = dishId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
}
