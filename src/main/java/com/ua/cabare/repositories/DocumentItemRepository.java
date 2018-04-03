package com.ua.cabare.repositories;

import com.ua.cabare.models.Document;
import com.ua.cabare.models.DocumentItem;
import com.ua.cabare.models.RawMaterial;
import com.ua.cabare.models.Stock;
import com.ua.cabare.models.StockActionReason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentItemRepository extends JpaRepository<DocumentItem, Long> {

  List<DocumentItem> getByDocument(Document document);

  @Query("select di from DocumentItem di "
      + " join Document d "
      + " where di.rawMaterial = ?3 and d.stockActionReason = ?4 "
      + " and di.stock = ?5 "
      + " and d.creationDate between ?1 and ?2 ")
  List<DocumentItem> getBy(LocalDate from, LocalDate to, RawMaterial rawMaterial,
      StockActionReason stockActionReason, Stock stock);
}
