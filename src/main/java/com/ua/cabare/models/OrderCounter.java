package com.ua.cabare.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_counter")
public class OrderCounter {

  @Id
  private Long id;

  @Column(name = "counter")
  private long counter;

  public OrderCounter() {
    this.id = 1L;
    this.counter = 0;
  }

  public long getCounter() {
    return counter;
  }

  public OrderCounter next() {
    ++counter;
    return this;
  }
}
