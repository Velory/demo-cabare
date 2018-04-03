package com.ua.cabare.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expenditure")
public class Expenditure extends EntityManager<Long, Expenditure> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "expenditure_title")
  private String expenditureTitle;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getExpenditureTitle() {
    return expenditureTitle;
  }

  public void setExpenditureTitle(String expenditureTitle) {
    this.expenditureTitle = expenditureTitle;
  }
}
