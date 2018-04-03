package com.ua.cabare.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import com.ua.cabare.domain.Money;
import com.ua.cabare.models.Discount;
import com.ua.cabare.repositories.DiscountRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceTest {

  @InjectMocks
  private DiscountService discountService;
  @Mock
  private DiscountRepository discountRepository;

  @Test
  public void changeDiscountSizeShouldUpdateSizeFrom10To15() throws Exception {
    int sizeOld = 10;
    int sizeNew = 15;
    Discount discountFromDb = new Discount();
    discountFromDb.setSize(sizeOld);

    when(discountRepository.findByCardNumber(anyString()))
        .thenReturn(Optional.of(discountFromDb));
    when(discountRepository.save(any(Discount.class))).then(invocation -> {
      Discount changedDiscount = invocation.getArgumentAt(0, Discount.class);
      assertEquals(sizeNew, changedDiscount.getSize());
      return changedDiscount;
    });

    discountService.changeDiscountSize("card number", sizeNew);
  }

  @Test
  public void addPayment() throws Exception {
    Money alreadyPaid = Money.valueOf(50);
    Money moreMoney = Money.valueOf(100);
    Discount discountFromDb = new Discount();
    discountFromDb.setTotalPaid(alreadyPaid);

    when(discountRepository.findByCardNumber(anyString()))
        .thenReturn(Optional.of(discountFromDb));
    when(discountRepository.save(any(Discount.class))).then(invocation -> {
      Discount changedDiscount = invocation.getArgumentAt(0, Discount.class);
      assertEquals(alreadyPaid.add(moreMoney), changedDiscount.getTotalPaid());
      return changedDiscount;
    });

    discountService.addPayment("card number", moreMoney);
  }

}