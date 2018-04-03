package com.ua.cabare.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "calculation")
public class Calculation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "is_archived", columnDefinition = "BIT(1) NULL DEFAULT 0")
  private Boolean isArchived;

  @Column(name = "quantity_for_dish")
  private double quantity;

  @Column(name = "begin_date", columnDefinition = "date")
  private LocalDate beginDate;

  @Column(name = "end_date", columnDefinition = "date")
  private LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "raw_material_id")
  private RawMaterial rawMaterial;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dish_id")
  private Dish dish;

  @OneToMany(mappedBy = "calculation", cascade = CascadeType.ALL)
  private List<CalculationAlternative> calculationAlternativeList;


  public Calculation() {
    this.isArchived = false;
    this.calculationAlternativeList = new ArrayList<>();
  }

  public void addAlternative(CalculationAlternative alternative) {
    this.calculationAlternativeList.add(alternative);
    alternative.setCalculation(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getArchived() {
    return isArchived;
  }

  public void setArchived(Boolean archived) {
    isArchived = archived;
  }

  public List<CalculationAlternative> getCalculationAlternativeList() {
    return calculationAlternativeList;
  }

  public void setCalculationAlternativeList(
      List<CalculationAlternative> calculationAlternativeList) {
    this.calculationAlternativeList = calculationAlternativeList;
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

  public Dish getDish() {
    return dish;
  }

  public void setDish(Dish dish) {
    this.dish = dish;
  }

  public LocalDate getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(LocalDate beginDate) {
    this.beginDate = beginDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "Calculation{" +
        "id=" + id +
        ", isArchived=" + isArchived +
        ", quantity=" + quantity +
        ", beginDate=" + beginDate +
        ", endDate=" + endDate +
        ", rawMaterial=" + rawMaterial +
        '}';
  }
}
