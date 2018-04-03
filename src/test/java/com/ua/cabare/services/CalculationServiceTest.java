package com.ua.cabare.services;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ua.cabare.TestUtils;
import com.ua.cabare.domain.calculation.CalculationDto;
import com.ua.cabare.models.Calculation;
import com.ua.cabare.models.CalculationAlternative;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.RawMaterial;
import com.ua.cabare.repositories.CalculationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CalculationServiceTest {

  @Spy
  @InjectMocks
  private CalculationService calculationService;
  @Mock
  private CalculationRepository calculationRepository;
  @Mock
  private DishService dishService;
  @Mock
  private RawMaterialService rawMaterialService;
  @Mock
  private TimeService timeService;

  CalculationDto[] calculationDtoList;
  Dish dish;
  LocalDate currentDate;
  RawMaterial rawMaterial_main;
  RawMaterial rawMaterial_1;
  RawMaterial rawMaterial_2;

  @Before
  public void init() throws Exception {
    calculationDtoList = TestUtils
        .readFromResources("add_calculation", CalculationDto[].class);

    currentDate = LocalDate.of(2017, 1, 11);
    when(timeService.getCurrentDate()).thenReturn(currentDate);

    dish = new Dish();
    when(dishService.findDish(anyLong())).thenReturn(dish);

    rawMaterial_main = new RawMaterial();
    rawMaterial_1 = new RawMaterial();
    rawMaterial_2 = new RawMaterial();
    when(rawMaterialService.findRawMaterial(anyLong()))
        .thenReturn(rawMaterial_main)
        .thenReturn(rawMaterial_1)
        .thenReturn(rawMaterial_2);

    when(calculationRepository.getActualCalculation(any(Dish.class)))
        .thenReturn(Arrays.asList(new Calculation()));
  }

  @Test
  public void addCalculation() throws Exception {
    when(calculationRepository.save(anyCollection()))
        .thenAnswer((Answer<List>) invocation -> {

          List<Calculation> calculationList = invocation.getArgumentAt(0, List.class);

          Calculation calculation = calculationList.get(0);
          assertThat(calculation.getDish()).isSameAs(dish);
          assertThat(calculation.getRawMaterial()).isSameAs(rawMaterial_main);
          assertThat(calculation.getQuantity()).isEqualTo(25);
          assertThat(calculation.getBeginDate()).isSameAs(currentDate);
          assertThat(calculation.getEndDate()).isNull();

          List<CalculationAlternative> alternatives = calculation.getCalculationAlternativeList();
          CalculationAlternative alternative_1 = alternatives.get(0);
          CalculationAlternative alternative_2 = alternatives.get(1);

          assertThat(alternative_1.getRawMaterial()).isSameAs(rawMaterial_1);
          assertThat(alternative_2.getRawMaterial()).isSameAs(rawMaterial_2);
          assertThat(alternative_1.getQuantity()).isEqualTo(30);
          assertThat(alternative_2.getQuantity()).isEqualTo(35);
          assertThat(alternative_1.getLevel()).isEqualTo(1);
          assertThat(alternative_2.getLevel()).isEqualTo(2);

          assertThat(calculationList.size()).isEqualTo(2);
          return calculationList;
        });

    calculationService.add(calculationDtoList, 1L);

    verify(calculationRepository, times(1)).save(anyCollectionOf(Calculation.class));
  }

  @Test
  public void updateCalculation() throws Exception {
    doNothing().when(calculationService).add(any(CalculationDto[].class), anyLong());

    when(calculationRepository.save(anyCollection())).thenAnswer((Answer<List>) invocation -> {

      List<Calculation> calculationList = invocation.getArgumentAt(0, List.class);
      Calculation calculation = calculationList.get(0);
      assertThat(calculation.getEndDate()).isSameAs(currentDate);
      return calculationList;
    });

    calculationService.update(calculationDtoList, 1L);

    verify(calculationRepository, times(1)).save(anyCollectionOf(Calculation.class));
  }
}