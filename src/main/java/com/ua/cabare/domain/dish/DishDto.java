package com.ua.cabare.domain.dish;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.domain.Money;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.DishCategory;
import com.ua.cabare.validation.Positive;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;


public class DishDto {

  @JsonProperty("id")
  public Long id;

  @NotEmpty(message = "name shouldn't be empty")
  @JsonProperty("name")
  public String name;

  @JsonProperty(value = "photo")
  public String photo;

  @Min(message = "dish_out should be positive", value = 0)
  @JsonProperty("dish_out")
  public int dishOut;

  @Positive(message = "price should be formatted like 123.45")
  @JsonProperty("price")
  public String price;

  @Min(message = "category_id should be positive", value = 0)
  @JsonProperty("category_id")
  public Long categoryId;

  @Min(message = "start_day should be positive", value = 0)
  @JsonProperty("start_day")
  public Integer startDay;

  @Min(message = "end_day should be positive", value = 0)
  @JsonProperty("end_day")
  public Integer endDay;

  @JsonProperty("quantity")
  public Integer quantity;

  public DishDto() {
  }

  public DishDto(Dish dish) {
    this.id = dish.getId();
    this.name = dish.getName();
    this.dishOut = dish.getDishOut();
    this.price = dish.getPrice().getValue();
    this.categoryId = dish.getDishCategory().getId();
    this.startDay = dish.getStartDay();
    this.endDay = dish.getEndDay();
    this.photo = dish.getPhoto() == null ? "no-photo.jpg" : dish.getPhoto();
  }

  public DishDto(Dish dish, Integer quantity) {
    this(dish);
    this.quantity = quantity;
  }

  public Dish build() {
    Dish dish = new Dish();
    DishCategory dishCategory = new DishCategory();
    dish.setId(this.id);
    dish.setName(this.name);
    dish.setDishOut(this.dishOut);
    dish.setPrice(new Money(this.price));
    dish.setStartDay(this.startDay);
    dish.setEndDay(this.endDay);
    dishCategory.setId(this.categoryId);
    dish.setDishCategory(dishCategory);
    return dish;
  }
}
