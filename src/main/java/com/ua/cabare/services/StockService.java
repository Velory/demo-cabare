package com.ua.cabare.services;

import com.ua.cabare.exceptions.StockNotFoundException;
import com.ua.cabare.models.Stock;
import com.ua.cabare.repositories.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

  @Autowired
  private StockRepository stockRepository;

  public Stock findById(Long stockId) {
    return stockRepository.findById(stockId).orElseThrow(() -> new StockNotFoundException());
  }

  public List<Stock> getAll() {
    return stockRepository.getVisible();
  }
}
