package com.ua.cabare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Money implements Serializable {

  @JsonIgnore
  public static final Money ZERO = new Money(0);
  @JsonIgnore
  private static final Pattern pattern = Pattern.compile("(?<=\\.)\\d{1,2}");
  @JsonIgnore
  private long rawValue;
  @JsonProperty(defaultValue = "0")
  private String value;

  public long getRawValue() {
    return rawValue;
  }

  public String getValue() {
    return String.format("%d.%02d", rawValue / 100, rawValue % 100);
  }

  public void setValue(String value) {
    this.value = value;
    this.rawValue = convertToLong(value);
  }

  public static Money valueOf(long value) {
    Money money = new Money();
    money.rawValue = value;
    return money;
  }

  private Money() {
    this.rawValue = 0;
  }

  public Money(long value) {
    this.rawValue = value * 100;
  }

  public Money(String value) {
    this.rawValue = convertToLong(value);
  }

  private static long convertToLong(String value) {
    String s = value.replaceAll("\\..*", "");
    Matcher matcher = pattern.matcher(value);
    if (matcher.find()) {
      String group = matcher.group();
      s += group.length() == 1 ? group + "0" : group;
    } else {
      s += "00";
    }
    return Long.parseLong(s);
  }


  public Money add(Money money) {
    return valueOf(money.rawValue + rawValue);
  }

  public Money subtract(Money money) {

    long diff = rawValue - money.rawValue;
    if (diff < 0) {
      throw new RuntimeException("Incorrect operation");
    }
    return valueOf(diff);
  }

  public Money multiply(long value) {
    return valueOf(this.rawValue * value);
  }

  public Money multiply(double value) {
    return valueOf(((long) (this.rawValue * value)));
  }

  public boolean isMoreThan(Money money) {
    return rawValue > money.rawValue;
  }

  public boolean isEqualTo(Money money) {
    return rawValue == money.rawValue;
  }

  public boolean isLessThan(Money money) {
    return rawValue < money.rawValue;
  }

  private boolean isZero() {
    return rawValue == 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Money money = (Money) o;
    return rawValue == money.rawValue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rawValue);
  }

  @Override
  public String toString() {
    return String.format("%d.%02d", rawValue / 100, rawValue % 100);
  }
}
