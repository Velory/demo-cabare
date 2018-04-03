package com.ua.cabare.domain.deliverynote;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class DeliveryNoteDto implements Serializable {

  private Long supplierId;
  private LocalDateTime purchaseDate;
  private String deliveryNoteNumber;
  private Long stockId;
  private Long stockActionReasonId;

  private List<DeliveryNoteItem> deliveryNoteItems;

  public DeliveryNoteDto() {
  }

  public DeliveryNoteDto(Long supplierId, LocalDateTime purchaseDate,
      String deliveryNoteNumber, Long stockId, Long stockActionReasonId,
      List<DeliveryNoteItem> deliveryNoteItems) {
    this.supplierId = supplierId;
    this.purchaseDate = purchaseDate;
    this.deliveryNoteNumber = deliveryNoteNumber;
    this.stockId = stockId;
    this.stockActionReasonId = stockActionReasonId;
    this.deliveryNoteItems = deliveryNoteItems;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public LocalDateTime getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(LocalDateTime purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getDeliveryNoteNumber() {
    return deliveryNoteNumber;
  }

  public void setDeliveryNoteNumber(String deliveryNoteNumber) {
    this.deliveryNoteNumber = deliveryNoteNumber;
  }

  public Long getStockId() {
    return stockId;
  }

  public void setStockId(Long stockId) {
    this.stockId = stockId;
  }

  public List<DeliveryNoteItem> getDeliveryNoteItems() {
    return deliveryNoteItems;
  }

  public void setDeliveryNoteItems(
      List<DeliveryNoteItem> deliveryNoteItems) {
    this.deliveryNoteItems = deliveryNoteItems;
  }

  public Long getStockActionReasonId() {
    return stockActionReasonId;
  }

  public void setStockActionReasonId(Long stockActionReasonId) {
    this.stockActionReasonId = stockActionReasonId;
  }
}
