package com.ua.cabare.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class VerificationToken extends EntityManager<Long, VerificationToken> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "employee_id",
      foreignKey = @ForeignKey(name = "FK_VERIFY_EMPLOYEE"))
  private Employee employee;

  private LocalDate expiryDate;

  public VerificationToken() {
  }

  public VerificationToken(String token, Employee employee) {
    this.token = token;
    this.employee = employee;
    this.expiryDate = calculateExpiryDate();
  }

  private LocalDate calculateExpiryDate() {
    return LocalDate.now().plusDays(1);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

}
