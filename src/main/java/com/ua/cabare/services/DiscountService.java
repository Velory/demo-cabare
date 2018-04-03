package com.ua.cabare.services;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.bill.DiscountDto;
import com.ua.cabare.exceptions.CardBlockedException;
import com.ua.cabare.exceptions.DiscountCardNotFoundException;
import com.ua.cabare.models.Discount;
import com.ua.cabare.repositories.DiscountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

  @Autowired
  private DiscountRepository discountRepository;
  @Autowired
  private TimeService timeService;

  public void emittDiscountCard(DiscountDto discountDto) {
    Discount discount = discountDto.build();
    discount.setActivated(true);
    discount.setTotalPaid(Money.ZERO);
    discount.setEmitted(timeService.getCurrentDate());
    discountRepository.save(discount);
  }

  public Discount getById(Long id) {
    return discountRepository.findById(id)
        .orElseThrow(() -> new DiscountCardNotFoundException());
  }

  public DiscountDto getDiscountCard(String cardNumber) {
    return new DiscountDto(findDiscountCard(cardNumber));
  }

  public void changeDiscountSize(String cardNumber, int newDiscountSize) {
    Discount discount = findDiscountCard(cardNumber);
    discount.setSize(newDiscountSize);
    discountRepository.save(discount);
  }

  public int getDiscountSize(String cardNumber) {
    Discount discount = findDiscountCard(cardNumber);
    return discount.getSize();
  }

  public boolean blockDiscountCard(String cardNumber) {
    Discount discount = findDiscountCard(cardNumber);
    discount.setActivated(false);
    discountRepository.save(discount);
    return true;
  }

  public void addPayment(String cardNumber, Money payment) {
    Discount discount = findDiscountCard(cardNumber);
    if (!discount.isActivated()) {
      throw new CardBlockedException();
    }
    Money totalPaid = discount.getTotalPaid().add(payment);
    discount.setTotalPaid(totalPaid);
    discountRepository.save(discount);

  }

  private Discount findDiscountCard(String cardNumber) {
    return discountRepository.findByCardNumber(cardNumber)
        .orElseThrow(() -> new DiscountCardNotFoundException());
  }
}
