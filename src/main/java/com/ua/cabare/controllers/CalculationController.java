package com.ua.cabare.controllers;

import com.ua.cabare.domain.calculation.CalculationDto;
import com.ua.cabare.services.CalculationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/calculation")
public class CalculationController {

  @Autowired
  private CalculationService calculationService;

  @RequestMapping(value = "/add", method = RequestMethod.PUT)
  public void addCalculation(@RequestBody @Valid CalculationDto[] calculationDtoList,
      @RequestParam(name = "dish_id") Long dishId) {
    calculationService.add(calculationDtoList, dishId);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public void update(@RequestBody @Valid CalculationDto[] calculationDtoList,
      @RequestParam(name = "dish_id") Long dishId) {
    calculationService.update(calculationDtoList, dishId);
  }

  @RequestMapping(value = "/get")
  public List<CalculationDto> getCalculation(@RequestParam(name = "dish_id") Long dishId) {
    return calculationService.getCalculation(dishId);
  }
}
