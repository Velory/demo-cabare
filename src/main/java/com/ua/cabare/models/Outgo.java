package com.ua.cabare.models;

import com.ua.cabare.domain.Money;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "outgo")
public class Outgo extends EntityManager<Long, Outgo> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "outgo_title")
  private String outgo_title;

  @Column(name = "outgo_date", columnDefinition = "date")
  private LocalDate outgoDate;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expenditure_id")
  private Expenditure expenditure;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @Column(name = "outgo_cost")
  @Convert(converter = MoneyConverter.class)
  private Money outgoCost;

  @Column(name = "cost_quantity")
  private int costQuantity;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getOutgo_title() {
    return outgo_title;
  }

  public void setOutgo_title(String outgo_title) {
    this.outgo_title = outgo_title;
  }

  public LocalDate getOutgoDate() {
    return outgoDate;
  }

  public void setOutgoDate(LocalDate outgoDate) {
    this.outgoDate = outgoDate;
  }

  public Expenditure getExpenditure() {
    return expenditure;
  }

  public void setExpenditure(Expenditure expenditure) {
    this.expenditure = expenditure;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Money getOutgoCost() {
    return outgoCost;
  }

  public void setOutgoCost(Money outgoCost) {
    this.outgoCost = outgoCost;
  }

  public int getCostQuantity() {
    return costQuantity;
  }

  public void setCostQuantity(int costQuantity) {
    this.costQuantity = costQuantity;
  }
}
