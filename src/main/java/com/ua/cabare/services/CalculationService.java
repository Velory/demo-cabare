package com.ua.cabare.services;

import com.ua.cabare.domain.calculation.CalculationAlternativeDto;
import com.ua.cabare.domain.calculation.CalculationDto;
import com.ua.cabare.exceptions.ActualCalculationExistException;
import com.ua.cabare.models.Calculation;
import com.ua.cabare.models.CalculationAlternative;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.RawMaterial;
import com.ua.cabare.repositories.CalculationRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculationService {

  @Autowired
  private CalculationRepository calculationRepository;

  @Autowired
  private DishService dishService;

  @Autowired
  private RawMaterialService rawMaterialService;

  @Autowired
  private TimeService timeService;
  private Logger logger = LogManager.getLogger();

  public void add(CalculationDto[] calculationDtoList, Long dishId) {
    long count = calculationRepository.getActualCount(dishId);
    if (count > 0) {
      throw new ActualCalculationExistException();
    }
    List<Calculation> calculationList = new ArrayList<>();
    for (CalculationDto calculationDto : calculationDtoList) {
      Calculation calculation = new Calculation();

      calculation.setDish(findDish(dishId));
      calculation.setQuantity(calculationDto.quantity);
      calculation.setRawMaterial(findRawMaterial(calculationDto.rawMaterialId));
      calculation.setBeginDate(timeService.getCurrentDate());

      for (CalculationAlternativeDto alternativeDto : calculationDto.alternativeDtos) {
        CalculationAlternative alternative = new CalculationAlternative();
        alternative.setRawMaterial(findRawMaterial(alternativeDto.rawMaterialId));
        alternative.setQuantity(alternativeDto.quantity);
        alternative.setLevel(alternativeDto.level);
        calculation.addAlternative(alternative);
      }
      calculationList.add(calculation);
    }
    calculationRepository.save(calculationList);
  }

  @Transactional
  public void update(CalculationDto[] calculationDtoList, Long dishId) {
    Dish dish = findDish(dishId);
    List<Calculation> calculationList = calculationRepository
        .getActualCalculation(dish);
    calculationList.forEach(item -> item.setEndDate(timeService.getCurrentDate()));
    calculationRepository.save(calculationList);
    add(calculationDtoList, dishId);
  }

  public List<CalculationDto> getCalculation(Long dishId) {
    Dish dish = findDish(dishId);
    return getCalculations(dish).stream()
        .map(calculation -> new CalculationDto(calculation))
        .collect(Collectors.toList());
  }

  public List<Calculation> getCalculations(Dish dish) {
    logger.entry(dish);
    return logger.traceExit(calculationRepository.getActualCalculation(dish));
  }

  private RawMaterial findRawMaterial(Long rawMaterialId) {
    return rawMaterialService.findRawMaterial(rawMaterialId);
  }

  private Dish findDish(Long dishId) {
    return dishService.findDish(dishId);
  }
}
