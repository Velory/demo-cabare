package com.ua.cabare.controllers;

import com.ua.cabare.domain.dish.DishDto;
import com.ua.cabare.services.DishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/dish")
public class DishController {

  @Autowired
  private DishService dishService;

  @RequestMapping(value = "/add", method = RequestMethod.PUT)
  public void addDish(@RequestBody @Valid DishDto dishDto) {
    dishService.addDish(dishDto);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public void update(@RequestBody @Valid DishDto dishDto) {
    dishService.update(dishDto);
  }

  @RequestMapping(value = "/get_all")
  public List<DishDto> getDishes(Pageable pageable) {
    return dishService.getDishes(pageable);
  }

  @RequestMapping(value = "/get_by_category")
  public List<DishDto> getByCategory(
      @RequestParam(name = "dish_category_id") Long dishCategoryId,
      Pageable pageable) {
    return dishService.getDishesByCategory(dishCategoryId, pageable);
  }
}
