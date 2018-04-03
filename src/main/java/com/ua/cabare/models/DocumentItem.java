package com.ua.cabare.models;

import com.ua.cabare.domain.Money;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "document_item")
public class DocumentItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "price")
  @Convert(converter = MoneyConverter.class)
  private Money price;

  @Column(name = "quantity")
  private double quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stock_id")
  private Stock stock;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "document_id")
  private Document document;

  @ManyToOne
  @JoinColumn(name = "raw_material_id")
  private RawMaterial rawMaterial;

  public DocumentItem() {
  }

  public DocumentItem(Money price) {
    this.price = price;
  }

  public DocumentItem(Money price, double quantity, Stock stock,
      RawMaterial rawMaterial) {
    this.price = price;
    this.quantity = quantity;
    this.stock = stock;
    this.rawMaterial = rawMaterial;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Money getPrice() {
    return price;
  }

  public void setPrice(Money price) {
    this.price = price;
  }

  public RawMaterial getRawMaterial() {
    return rawMaterial;
  }

  public void setRawMaterial(RawMaterial rawMaterial) {
    this.rawMaterial = rawMaterial;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentItem that = (DocumentItem) o;
    return Double.compare(that.quantity, quantity) == 0 &&
        Objects.equals(price, that.price) &&
        Objects.equals(stock, that.stock) &&
        Objects.equals(document, that.document) &&
        Objects.equals(rawMaterial, that.rawMaterial);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, quantity, stock, document, rawMaterial);
  }
}
