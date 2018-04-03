package com.ua.cabare.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ua.cabare.domain.Gender;
import com.ua.cabare.domain.Money;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "discount")
public class Discount extends EntityManager<Long, Discount> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "gender", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private Gender gender;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @Column(name = "birthday", columnDefinition = "date")
  private LocalDate birthday;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @Column(name = "emitted", columnDefinition = "date")
  private LocalDate emitted;

  @Column(name = "total_paid")
  @Convert(converter = MoneyConverter.class)
  private Money totalPaid;

  @Column(name = "discount_size")
  private int size;

  @Column(name = "activated")
  private boolean activated;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "discount")
  private Set<Bill> bills;

  public Money getTotalPaid() {
    return totalPaid;
  }

  public void setTotalPaid(Money totalPaid) {
    this.totalPaid = totalPaid;
  }

  public Set<Bill> getBills() {
    return bills;
  }

  public void setBills(Set<Bill> bills) {
    this.bills = bills;
  }

  public boolean isActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public LocalDate getEmitted() {
    return emitted;
  }

  public void setEmitted(LocalDate emitted) {
    this.emitted = emitted;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }
}
