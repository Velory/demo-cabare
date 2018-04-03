package com.ua.cabare.repositories;

import com.ua.cabare.domain.Money;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.DishCategory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends CrudRepository<Dish, Long> {

  Optional<Dish> findById(long id);

  Optional<Dish> findByName(String name);

  @Query("SELECT d FROM Dish d WHERE d.startDay <= ?1 and d.endDay >= ?1 and d.isArchived = false")
  List<Dish> streamAllPaged(int dayOfYear, Pageable pageable);

  @Query("select d from Dish d where d.dishCategory = ?1 and "
      + " d.startDay <= ?2 and d.endDay >= ?2")
  List<Dish> getDishes(DishCategory dishCategory, int dayOfYear, Pageable pageable);

  @Query("SELECT d FROM Dish d WHERE d.price >= ?1 and d.price <= ?2")
  List<Dish> getDishesByPrice(Money from, Money to);

  Dish save(Dish dish);
}
