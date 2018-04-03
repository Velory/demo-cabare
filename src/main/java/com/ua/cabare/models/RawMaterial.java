package com.ua.cabare.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ua.cabare.domain.calculation.RawMaterialDto;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "raw_material")
public class RawMaterial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "measure_unit")
  private String measureUnit;

  @Column(name = "threshold")
  private int threshold;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "rawMaterial")
  private List<StockItem> stockItems;

  public RawMaterial() {

  }

  public RawMaterial(RawMaterialDto rawMaterialDto) {
    this.name = rawMaterialDto.name.trim().replaceAll("\\s+", " ");
    this.measureUnit = rawMaterialDto.measureUnit;
    this.threshold = rawMaterialDto.threshold;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMeasureUnit() {
    return measureUnit;
  }

  public void setMeasureUnit(String measureUnit) {
    this.measureUnit = measureUnit;
  }

  public int getThreshold() {
    return threshold;
  }

  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  public List<StockItem> getStockItems() {
    return stockItems;
  }

  public void setStockItems(List<StockItem> stockItems) {
    this.stockItems = stockItems;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RawMaterial that = (RawMaterial) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(measureUnit, that.measureUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, measureUnit);
  }

  @Override
  public String toString() {
    return "RawMaterial{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", measureUnit='" + measureUnit + '\'' +
        '}';
  }
}
