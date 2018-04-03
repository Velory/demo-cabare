package com.ua.cabare.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock extends EntityManager<Long, Stock> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "photo")
  private String photo;

  @Column(name = "is_visible")
  private boolean isVisible;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
  private Set<DocumentItem> documentItems = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
  private Set<StockItem> stockItems = new HashSet<>();


  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public boolean getVisible() {
    return isVisible;
  }

  public void setVisible(boolean visible) {
    isVisible = visible;
  }

  public Set<DocumentItem> getDocumentItems() {
    return documentItems;
  }

  public void setDocumentItems(Set<DocumentItem> documentItems) {
    this.documentItems = documentItems;
  }

  public Set<StockItem> getStockItems() {
    return stockItems;
  }

  public void setStockItems(Set<StockItem> stockItems) {
    this.stockItems = stockItems;
  }
}
