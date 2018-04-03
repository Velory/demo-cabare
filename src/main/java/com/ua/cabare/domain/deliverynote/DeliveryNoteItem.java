package com.ua.cabare.domain.deliverynote;

import java.io.Serializable;
import java.time.LocalDate;

public class DeliveryNoteItem implements Serializable {

  private Long rawMaterialId;
  private String mesureUnit;
  private LocalDate lastPurchase;
  private Double lastPurchasePrice;
  private Double quantity;
  private Long price;

  public DeliveryNoteItem() {
  }

  public DeliveryNoteItem(Long rawMaterialId, String mesureUnit, LocalDate lastPurchase,
      Double lastPurchasePrice, Double quantity, Long price) {
    this.rawMaterialId = rawMaterialId;
    this.mesureUnit = mesureUnit;
    this.lastPurchase = lastPurchase;
    this.lastPurchasePrice = lastPurchasePrice;
    this.quantity = quantity;
    this.price = price;
  }

  public Long getRawMaterialId() {
    return rawMaterialId;
  }

  public void setRawMaterialId(Long rawMaterialId) {
    this.rawMaterialId = rawMaterialId;
  }

  public String getMesureUnit() {
    return mesureUnit;
  }

  public void setMesureUnit(String mesureUnit) {
    this.mesureUnit = mesureUnit;
  }

  public LocalDate getLastPurchase() {
    return lastPurchase;
  }

  public void setLastPurchase(LocalDate lastPurchase) {
    this.lastPurchase = lastPurchase;
  }

  public Double getLastPurchasePrice() {
    return lastPurchasePrice;
  }

  public void setLastPurchasePrice(Double lastPurchasePrice) {
    this.lastPurchasePrice = lastPurchasePrice;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }
}
