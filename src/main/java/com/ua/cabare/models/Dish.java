package com.ua.cabare.models;

import com.ua.cabare.domain.Money;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dish")
public class Dish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "photo")
  private String photo;

  @Column(name = "dish_out")
  private Integer dishOut;

  @Column(name = "start_day", columnDefinition = "INT DEFAULT 0")
  private Integer startDay;

  @Column(name = "end_day", columnDefinition = "INT DEFAULT 366")
  private Integer endDay;

  @Column(name = "price")
  @Convert(converter = MoneyConverter.class)
  private Money price;

  @Column(name = "is_archived", columnDefinition = "BIT(1) DEFAULT 0")
  private Boolean isArchived = false;

  @ManyToOne
  @JoinColumn(name = "dish_category_id", nullable = false)
  private DishCategory dishCategory;


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

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public DishCategory getDishCategory() {
    return dishCategory;
  }

  public void setDishCategory(DishCategory dishCategory) {
    this.dishCategory = dishCategory;
  }

  public Money getPrice() {
    return price;
  }

  public void setPrice(Money price) {
    this.price = price;
  }

  public int getDishOut() {
    return dishOut;
  }

  public void setDishOut(int dishOut) {
    this.dishOut = dishOut;
  }

  public Integer getStartDay() {
    return startDay;
  }

  public void setStartDay(Integer startDay) {
    this.startDay = startDay;
  }

  public Integer getEndDay() {
    return endDay;
  }

  public void setEndDay(Integer endDay) {
    this.endDay = endDay;
  }

  public void setDishOut(Integer dishOut) {
    this.dishOut = dishOut;
  }

  public Boolean getArchived() {
    return isArchived;
  }

  public void setArchived(Boolean archived) {
    isArchived = archived;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dish dish = (Dish) o;
    return Objects.equals(name, dish.name) &&
        Objects.equals(dishOut, dish.dishOut);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dishOut);
  }

  @Override
  public String toString() {
    return "Dish{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", dishOut=" + dishOut +
        ", startDay=" + startDay +
        ", endDay=" + endDay +
        ", price=" + price +
        ", isArchived=" + isArchived +
        '}';
  }
}


