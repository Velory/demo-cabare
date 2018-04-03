package com.ua.cabare.domain.dish;

import com.ua.cabare.models.Stock;

public class StockDto {

  private Long id;
  private String name;
  private String photo;

  public StockDto(Stock stock) {
    this.id = stock.getId();
    this.name = stock.getTitle();
    String photo = stock.getPhoto();
    this.photo = photo == null ? "no-photo.jpg" : photo;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhoto() {
    return photo;
  }
}
