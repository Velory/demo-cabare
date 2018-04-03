package com.ua.cabare.controllers;

import com.ua.cabare.domain.dish.DishCategoryDto;
import com.ua.cabare.services.DishCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/dish_category")
public class DishCategoryController {

  @Autowired
  private DishCategoryService dishCategoryService;

  @RequestMapping(value = "/add", method = RequestMethod.PUT)
  public DishCategoryDto addNew(@RequestBody @Valid DishCategoryDto dishCategoryDto) {
    return new DishCategoryDto(dishCategoryService.addNew(dishCategoryDto));
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public DishCategoryDto update(@RequestBody @Valid DishCategoryDto dishCategoryDto) {
    return new DishCategoryDto(dishCategoryService.update(dishCategoryDto));
  }

  @RequestMapping(value = "/get_dish_categories")
  public List<DishCategoryDto> getCategories(@RequestParam(name = "stock_id") Long stockId,
      Pageable pageable) {
    return dishCategoryService.getCategories(stockId, pageable).stream()
        .map(dc -> new DishCategoryDto(dc))
        .collect(Collectors.toList());
  }
}
