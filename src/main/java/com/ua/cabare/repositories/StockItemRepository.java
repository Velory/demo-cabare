package com.ua.cabare.repositories;

import com.ua.cabare.models.StockItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {

  @Query(" select si from StockItem si where si.stock.id = ?1 and si.rawMaterial.id = ?2")
  Optional<StockItem> findByStockAndRawMaterial(Long stockId, Long rawMaterialId);

  @Query(" select si from StockItem si where si.stock.id = ?1 and si.rawMaterial.id in ?2")
  List<StockItem> findByStockIdAndRawIds(Long stockId, List<Long> rawMaterialIds);
}
