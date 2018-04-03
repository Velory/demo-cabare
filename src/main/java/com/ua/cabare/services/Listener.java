package com.ua.cabare.services;

import com.ua.cabare.domain.listener.WriteOffEvent;
import com.ua.cabare.exceptions.ActualCalculationNotFoundException;
import com.ua.cabare.exceptions.NotEnoughStockItemException;
import com.ua.cabare.exceptions.StockItemNotFoundException;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Calculation;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.OrderItem;
import com.ua.cabare.models.StockItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Component
public class Listener {

  @Autowired
  private StockItemService stockItemService;
  @Autowired
  private CalculationService calculationService;
  private Logger logger = LogManager.getLogger();
  @Autowired
  private ApplicationEventPublisher publisher;


  @Transactional
  public void onOrderingWriteOff(List<OrderItem> orderItems, Bill bill) {
    logger.entry(orderItems);

    Map<Long, List<Calculation>> calculationMap = new HashMap<>();
    for (OrderItem orderItem : orderItems) {
      Dish dish = orderItem.getDish();
      Long stockId = dish.getDishCategory().getStock().getId();
      logger.trace("Dish: {} from Stock: {}", dish, stockId);
      calculationMap.putIfAbsent(stockId, new ArrayList<>());
      List<Calculation> calculations = calculationService.getCalculations(dish);
      if (calculations.isEmpty()) {
        throw new ActualCalculationNotFoundException(dish.getName());
      }
      for (int i = 0; i < orderItem.getQuantity(); i++) {
        calculationMap.get(stockId).addAll(calculations);
      }
    }
    for (Entry<Long, List<Calculation>> longListEntry : calculationMap.entrySet()) {
      Long stockId = longListEntry.getKey();
      List<Calculation> calculations = longListEntry.getValue();
      List<Long> ids = calculations.stream()
          .map(c -> c.getRawMaterial().getId())
          .collect(Collectors.toList());
      logger.trace("RawMaterial ids:{}", ids);
      List<StockItem> stockItemList = stockItemService.findStockItems(stockId, ids);
      logger.trace("Items found at stock: {}", stockItemList);
      writeOff(calculations, stockItemList, stockId);
      logger.trace("Items changed, to be stored: {}", stockItemList);
      stockItemService.save(stockItemList);
    }
    publisher.publishEvent(new WriteOffEvent(bill, calculationMap));
    logger.traceExit();
  }

  private void writeOff(List<Calculation> calculations, List<StockItem> stockItems, Long stockId) {
    for (Calculation calculation : calculations) {
      Long rawId = calculation.getRawMaterial().getId();
      StockItem stockItem = stockItems.stream()
          .filter(si -> rawId.equals(si.getRawMaterial().getId()))
          .findFirst()
          .orElseThrow(() -> new StockItemNotFoundException(
              MessageFormat.format("there is no product with id:{0}, in stock:{1}", rawId, stockId)
          ));
      double quantity = stockItem.getStockItemQuantity() - calculation.getQuantity();
      if (quantity < 0) {
        throw new NotEnoughStockItemException("not enough " + stockItem.getRawMaterial().getName());
      }
      stockItem.setStockItemQuantity(quantity);
    }
  }
}
