package com.ua.cabare.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dish_category")
public class DishCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "photo")
  private String photo;

  @OneToMany(mappedBy = "dishCategory")
  Set<Dish> dishes = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "stock_id")
  private Stock stock;

  public void addDish(Dish dish) {
    this.dishes.add(dish);
    dish.setDishCategory(this);
  }

  public void removeDish(Dish dish) {
    this.dishes.remove(dish);
    dish.setDishCategory(null);
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
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

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Set<Dish> getDishes() {
    return dishes;
  }

  public void setDishes(Set<Dish> dishes) {
    this.dishes = dishes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DishCategory that = (DishCategory) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
