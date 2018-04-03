package com.ua.cabare.controllers;

import com.ua.cabare.domain.dish.StockDto;
import com.ua.cabare.services.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stock")
public class StockController {

  @Autowired
  private StockService stockService;

  @RequestMapping(value = "/get_all")
  public List<StockDto> getAll() {
    return stockService.getAll().stream()
        .map(stock -> new StockDto(stock))
        .collect(Collectors.toList());
  }
}
