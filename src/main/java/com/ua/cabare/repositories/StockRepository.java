package com.ua.cabare.repositories;

import com.ua.cabare.models.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

  Optional<Stock> findById(Long stockId);

  @Query("select s from Stock s where s.isVisible = true")
  List<Stock> getVisible();
}
