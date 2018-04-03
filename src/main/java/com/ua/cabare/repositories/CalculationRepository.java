package com.ua.cabare.repositories;

import com.ua.cabare.models.Calculation;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.RawMaterial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

  @Query("select c from Calculation c where c.endDate = null and c.dish = ?1")
  List<Calculation> getActualCalculation(Dish dish);

  @Query("select c from Calculation c where c.dish = ?1")
  List<Calculation> getAllByDish(Dish dish);

  @Query("select c from Calculation c where c.endDate = null and c.rawMaterial = ?1")
  List<Calculation> getActualCalculationByRawMaterial(RawMaterial rawMaterial);

  @Query("select count(c) from Calculation c where c.dish.id = ?1 and c.endDate = null")
  Long getActualCount(Long dishId);
}
