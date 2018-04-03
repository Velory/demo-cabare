package com.ua.cabare.controllers;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.bill.DiscountDto;
import com.ua.cabare.services.DiscountService;
import com.ua.cabare.validation.Positive;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/discountcard")
public class DiscountController {

  @Autowired
  private DiscountService discountService;

  @RequestMapping(value = "/emittcard", method = RequestMethod.PUT)
  public void emittCard(@RequestBody @Valid DiscountDto discountDto) {
    discountService.emittDiscountCard(discountDto);
  }

  @RequestMapping(value = "/getcard/{discount_card}", method = RequestMethod.GET)
  public DiscountDto getCard(
      @PathVariable(name = "discount_card")
      @NotBlank(message = "discount card should be specified")
          String discountCard) {
    return discountService.getDiscountCard(discountCard);
  }

  @RequestMapping(value = "/editsize", method = RequestMethod.POST)
  public void editCard(
      @RequestParam(name = "discount_card")
      @NotBlank(message = "discount card should be specified")
          String discountCard,
      @RequestParam(name = "discount_size")
      @NotNull(message = "discount size should be specified")
      @Min(value = 0, message = "min discount: 0")
      @Max(value = 100, message = "max discount: 100")
          Integer newDiscountSize) {
    discountService.changeDiscountSize(discountCard, newDiscountSize);
  }

  @RequestMapping(value = "/getsize/{discount_card}", method = RequestMethod.GET)
  public Integer getSize(
      @PathVariable(name = "discount_card")
      @NotBlank(message = "discount card should be specified")
          String discountCard) {
    return discountService.getDiscountSize(discountCard);
  }

  @RequestMapping(value = "/block", method = RequestMethod.POST)
  public void blockCard(
      @RequestParam(name = "discount_card")
      @NotBlank(message = "discount card should be specified")
          String discountCard) {
    discountService.blockDiscountCard(discountCard);
  }

  @RequestMapping(value = "/addpayment", method = RequestMethod.POST)
  public void addPayment(
      @RequestParam(name = "discount_card")
      @NotBlank(message = "discount card should be specified")
          String discountCard,
      @RequestParam(name = "add_payment")
      @Positive(message = "payment sum should be formatted like 123.45")
          String paymentStr) {
    Money payment = new Money(paymentStr);
    discountService.addPayment(discountCard, payment);
  }
}
