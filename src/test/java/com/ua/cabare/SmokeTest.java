package com.ua.cabare;

import static org.assertj.core.api.Assertions.assertThat;

import com.ua.cabare.controllers.BillController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SmokeTest {

  @Autowired
  private BillController billController;

  @Test
  public void contextLoad() {
    assertThat(billController).isNotNull();
  }
}
