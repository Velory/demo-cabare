package com.ua.cabare.domain.dish;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.models.DishCategory;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class DishCategoryDto {

  @JsonProperty(value = "id")
  private Long id;
  @NotBlank(message = "category name should be specified")
  @JsonProperty(value = "name")
  private String name;
  @NotNull(message = "stock id should be specified")
  @JsonProperty(value = "stock_id")
  private Long stockId;
  @JsonProperty(value = "photo")
  private String photo;

  public DishCategoryDto() {
  }

  public DishCategoryDto(DishCategory dishCategory) {
    this.id = dishCategory.getId();
    this.name = dishCategory.getName();
    this.stockId = dishCategory.getStock().getId();
    String photo = dishCategory.getPhoto();
    this.photo = photo == null ? "no-photo.jpg" : photo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getStockId() {
    return stockId;
  }

  public void setStockId(Long stockId) {
    this.stockId = stockId;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }
}
