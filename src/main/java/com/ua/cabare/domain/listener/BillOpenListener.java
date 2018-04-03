package com.ua.cabare.domain.listener;

import com.ua.cabare.domain.Money;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Calculation;
import com.ua.cabare.models.Document;
import com.ua.cabare.models.DocumentItem;
import com.ua.cabare.models.RawMaterial;
import com.ua.cabare.models.Stock;
import com.ua.cabare.services.StockActionReasonService;
import com.ua.cabare.services.StockService;
import com.ua.cabare.services.TimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class BillOpenListener implements ApplicationListener<WriteOffEvent> {

  @Autowired
  private StockActionReasonService stockActionReasonService;
  @Autowired
  private DocumentService documentService;
  @Autowired
  private TimeService timeService;
  @Autowired
  private StockService stockService;
  private static Map<Long, Stock> stockMap = new HashMap<>();

  @Override
  @Transactional
  public void onApplicationEvent(WriteOffEvent event) {
    Bill bill = (Bill) event.getSource();
    Long reasonId = event.getReasonId();
    Document document = documentService.findByBill(bill)
        .orElse(new Document(stockActionReasonService.getReason(reasonId), bill));
    document.setDocumentDate(timeService.getCurrentDate());
    Map<Long, List<Calculation>> calculationMap = event.getCalculationMap();
    for (Entry<Long, List<Calculation>> stockCalculationListEntry : calculationMap.entrySet()) {
      Long stockId = stockCalculationListEntry.getKey();
      List<Calculation> calculations = stockCalculationListEntry.getValue();

      Stock stock = stockMap.computeIfAbsent(stockId, stockService::findById);

      for (Calculation calculation : calculations) {
        double quantity = calculation.getQuantity();
        RawMaterial rawMaterial = calculation.getRawMaterial();
        DocumentItem documentItem = new DocumentItem(Money.ZERO, quantity, stock, rawMaterial);
        document.addDocumentItem(documentItem);
      }
    }

    documentService.save(document);
  }
}
