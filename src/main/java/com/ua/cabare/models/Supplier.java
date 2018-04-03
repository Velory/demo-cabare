package com.ua.cabare.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier extends EntityManager<Long, Supplier> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "supplier_name")
  private String supplierName;

  @Column(name = "deferral")
  private int deferral;

  @Column(name = "start_contract", columnDefinition = "date")
  private LocalDate startContract;

  @Column(name = "end_contract", columnDefinition = "date")
  private LocalDate endCotract;

  @Column(name = "contract_type")
  private String contractType;

  @Column(name = "contract_number")
  private String contractNumber;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public int getDeferral() {
    return deferral;
  }

  public void setDeferral(int deferral) {
    this.deferral = deferral;
  }

  public LocalDate getStartContract() {
    return startContract;
  }

  public void setStartContract(LocalDate startContract) {
    this.startContract = startContract;
  }

  public LocalDate getEndCotract() {
    return endCotract;
  }

  public void setEndCotract(LocalDate endCotract) {
    this.endCotract = endCotract;
  }

  public String getContractType() {
    return contractType;
  }

  public void setContractType(String contractType) {
    this.contractType = contractType;
  }

  public String getContractNumber() {
    return contractNumber;
  }

  public void setContractNumber(String contractNumber) {
    this.contractNumber = contractNumber;
  }
}
