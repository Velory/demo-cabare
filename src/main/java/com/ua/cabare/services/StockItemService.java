package com.ua.cabare.services;

import com.ua.cabare.exceptions.StockItemNotFoundException;
import com.ua.cabare.models.StockItem;
import com.ua.cabare.repositories.StockItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StockItemService {

  @Autowired
  private StockItemRepository stockItemRepository;

  public StockItem findByStockAndRawMaterial(Long stockId, Long rawMaterialId) {
    return stockItemRepository.findByStockAndRawMaterial(stockId, rawMaterialId)
        .orElseThrow(() -> new StockItemNotFoundException());
  }

  public List<StockItem> findStockItems(Long stockId, List<Long> ids) {
    return stockItemRepository.findByStockIdAndRawIds(stockId, ids);
  }

  public void save(Collection<StockItem> stockItems) {
    stockItemRepository.save(stockItems);
  }
}
