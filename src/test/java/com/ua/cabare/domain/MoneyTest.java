package com.ua.cabare.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoneyTest {

  private Money firstMoney = new Money("120.35");
  private Money secondMoney = new Money("120.65");


  @Test
  public void correctProcessedInputData() {

    assertEquals(12345, new Money("123.45").getRawValue());
    assertEquals(12340, new Money("123.4usd").getRawValue());
    assertEquals(12300, new Money("123.").getRawValue());
    assertEquals(12300, new Money("123").getRawValue());
    assertEquals(12345, new Money("123.456").getRawValue());
    assertEquals(145, new Money("1.456").getRawValue());
    assertEquals(45, new Money(".456").getRawValue());
  }

  @Test
  public void sum() {
    assertEquals("241.00", firstMoney.add(secondMoney).getValue());
  }

  @Test
  public void subtract() {
    assertEquals("0.30", secondMoney.subtract(firstMoney).getValue());
  }

  @Test(expected = RuntimeException.class)
  public void subtractShouldThrowRuntimeException() {
    firstMoney.subtract(secondMoney);
  }

  @Test
  public void multiply(){
    assertEquals("240.70", firstMoney.multiply(2).getValue());
    assertEquals("12.03", firstMoney.multiply(0.1).getValue());
  }
}