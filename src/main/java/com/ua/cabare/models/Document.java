package com.ua.cabare.models;

import com.ua.cabare.domain.Money;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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
@Table(name = "document")
public class Document extends EntityManager<Long, Document> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "stock_action_date", columnDefinition = "datetime")
  private LocalDateTime creationDate;

  @Column(name = "document_date", columnDefinition = "date")
  private LocalDate documentDate;

  @Convert(converter = MoneyConverter.class)
  @Column(name = "total_sum")
  private Money totalSum = Money.ZERO;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stock_action_reason_id")
  private StockActionReason stockActionReason;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bill_id")
  private Bill bill;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;

  @Column(name = "document_number")
  private String documentNumber;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "document", cascade = {CascadeType.ALL})
  private Set<DocumentItem> documentItems = new HashSet<>();

  public Document() {
  }

  public Document(StockActionReason stockActionReason, Bill bill) {
    this.creationDate = bill.getOpenBillTime();
    this.employee = bill.getEmployee();
    this.stockActionReason = stockActionReason;
    this.bill = bill;
  }

  public void addDocumentItem(DocumentItem documentItem) {
    this.documentItems.add(documentItem);
    documentItem.setDocument(this);
  }

  public void removeDOcumentItem(DocumentItem documentItem) {
    this.documentItems.remove(documentItem);
    documentItem.setDocument(null);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public LocalDate getDocumentDate() {
    return documentDate;
  }

  public void setDocumentDate(LocalDate documentDate) {
    this.documentDate = documentDate;
  }

  public StockActionReason getStockActionReason() {
    return stockActionReason;
  }

  public void setStockActionReason(StockActionReason stockActionReason) {
    this.stockActionReason = stockActionReason;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public Set<DocumentItem> getDocumentItems() {
    return documentItems;
  }

  public void setDocumentItems(Set<DocumentItem> documentItems) {
    this.documentItems = documentItems;
  }

  public Bill getBill() {
    return bill;
  }

  public void setBill(Bill bill) {
    this.bill = bill;
  }

  public Money getTotalSum() {
    return totalSum;
  }

  public void setTotalSum(Money totalSum) {
    this.totalSum = totalSum;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
