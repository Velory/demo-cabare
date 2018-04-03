package com.ua.cabare.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shift_status")
public class ShiftStatus extends EntityManager<Long, ShiftStatus> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "is_shift_closed", columnDefinition = "BIT(1) DEFAULT 0")
  private boolean shiftClosed;

  @Column(name = "is_cash_closed", columnDefinition = "BIT(1)  DEFAULT 0")
  private boolean cashClosed;

  public ShiftStatus() {
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public boolean isShiftClosed() {
    return shiftClosed;
  }

  public void setShiftClosed(boolean shiftClosed) {
    this.shiftClosed = shiftClosed;
  }

  public boolean isCashClosed() {
    return cashClosed;
  }

  public void setCashClosed(boolean cashClosed) {
    this.cashClosed = cashClosed;
  }
}
