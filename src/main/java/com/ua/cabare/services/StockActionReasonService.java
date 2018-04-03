package com.ua.cabare.services;

import com.ua.cabare.models.StockActionReason;
import com.ua.cabare.repositories.StockActionReasonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockActionReasonService {

  @Autowired
  private StockActionReasonRepository stockActionReasonRepository;

  public StockActionReason getReason(Long reasonId) {
    return stockActionReasonRepository.findById(reasonId)
        .orElseThrow(() -> new RuntimeException("no reason found in db"));
  }
}
