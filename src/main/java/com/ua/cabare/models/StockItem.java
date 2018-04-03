package com.ua.cabare.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock_item")
public class StockItem extends EntityManager<Long, StockItem> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "stock_item_quantity")
  private double stockItemQuantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "raw_material_id")
  private RawMaterial rawMaterial;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stock_id")
  private Stock stock;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "stock_item_document",
      joinColumns = {@JoinColumn(name = "stock_item_id")},
      inverseJoinColumns = {@JoinColumn(name = "document_id")})
  private Set<Document> documents;


  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public double getStockItemQuantity() {
    return stockItemQuantity;
  }

  public void setStockItemQuantity(double stockItemQuantity) {
    this.stockItemQuantity = stockItemQuantity;
  }

  public RawMaterial getRawMaterial() {
    return rawMaterial;
  }

  public void setRawMaterial(RawMaterial rawMaterial) {
    this.rawMaterial = rawMaterial;
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }

  public Set<Document> getDocuments() {
    return documents;
  }

  public void setDocuments(Set<Document> documents) {
    this.documents = documents;
  }

  @Override
  public String toString() {
    return "StockItem{" +
        "id=" + id +
        ", stockItemQuantity=" + stockItemQuantity +
        ", rawMaterial=" + rawMaterial +
        '}';
  }
}
