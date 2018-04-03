package com.ua.cabare.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "calculation_alternative")
public class CalculationAlternative {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "level")
  private Integer level;

  @Column(name = "quantity_for_dish")
  private double quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "raw_material_id")
  private RawMaterial rawMaterial;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "calculation_id")
  private Calculation calculation;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public RawMaterial getRawMaterial() {
    return rawMaterial;
  }

  public void setRawMaterial(RawMaterial rawMaterial) {
    this.rawMaterial = rawMaterial;
  }

  public Calculation getCalculation() {
    return calculation;
  }

  public void setCalculation(Calculation calculation) {
    this.calculation = calculation;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }
}
