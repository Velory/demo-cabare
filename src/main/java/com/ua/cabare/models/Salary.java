package com.ua.cabare.models;

import com.ua.cabare.domain.Money;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salary")
public class Salary extends EntityManager<Long, Salary> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "total_salary")
  @Convert(converter = MoneyConverter.class)
  private Money totalSalary;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Money getTotalSalary() {
    return totalSalary;
  }

  public void setTotalSalary(Money totalSalary) {
    this.totalSalary = totalSalary;
  }
}
