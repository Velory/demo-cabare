package com.ua.cabare.services;

import com.ua.cabare.domain.Utils;
import com.ua.cabare.domain.dish.DishDto;
import com.ua.cabare.exceptions.DishCategoryNotSpecifiedException;
import com.ua.cabare.exceptions.DishNotFoundException;
import com.ua.cabare.exceptions.DishNotSpecifiedException;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.DishCategory;
import com.ua.cabare.repositories.DishRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DishService {

  @Autowired
  private DishRepository dishRepository;
  @Autowired
  DishCategoryService dishCategoryService;
  @Autowired
  private TimeService timeService;
  @Autowired
  private StockService stockService;

  public Dish findDish(Long dishId) {
    if (dishId == null) {
      throw new DishNotSpecifiedException();
    }
    return dishRepository.findById(dishId).orElseThrow(() -> new DishNotFoundException());
  }

  public Dish addDish(DishDto dishDto) {
    Dish dish = dishDto.build();
    Long categoryId = dish.getDishCategory().getId();
    if (categoryId == null) {
      throw new DishCategoryNotSpecifiedException();
    }

    String name = dish.getName();
    if (name == null) {
      throw new RuntimeException("dish name should be specified");
    }
    if (dishRepository.findByName(name).isPresent()) {
      throw new RuntimeException("such dish has allready defined in db");
    }
    DishCategory category = dishCategoryService.findById(categoryId);
    dish.setDishCategory(category);
    return dishRepository.save(dish);
  }

  public Dish update(DishDto dishDto) {
    Long dishId = dishDto.id;
    if (dishId == null) {
      throw new DishNotSpecifiedException();
    }
    Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new DishNotFoundException());
    Dish toStored = findDish(dishId);
    Dish build = dishDto.build();

    String name = build.getName();
    Optional<Dish> byName = dishRepository.findByName(name);
    if (name != null && byName.isPresent()) {
      if (byName.get().getId() != dish.getId()) {
        throw new RuntimeException("such dish has allready defined in db");
      }
    }

    DishCategory currentCategory = dish.getDishCategory();
    Long newCategoryId = dishDto.categoryId;
    if (newCategoryId != null && newCategoryId != currentCategory.getId()) {
      DishCategory newCategory = dishCategoryService.findById(newCategoryId);
      build.setDishCategory(newCategory);
    }
    Dish dishUpdated = Utils.updateState(toStored, build);
    return dishRepository.save(dishUpdated);
  }

  public List<DishDto> getDishes(Pageable pageable) {
    int dayOfYear = timeService.getCurrentTime().getDayOfYear();
    return dishRepository.streamAllPaged(dayOfYear, pageable).stream()
        .map(dish -> new DishDto(dish))
        .collect(Collectors.toList());
  }

  public List<DishDto> getDishesByCategory(Long dishCategoryId, Pageable pageable) {
    DishCategory category = dishCategoryService.findById(dishCategoryId);
    int dayOfYear = timeService.getCurrentTime().getDayOfYear();
    List<Dish> dishesByDishCategory = dishRepository
        .getDishes(category, dayOfYear, pageable);
    return dishesByDishCategory.stream()
        .map(dish -> new DishDto(dish, getQuantity(dish)))
        .collect(Collectors.toList());
  }

  private Map<Long, Integer> dishQuantityMap = new HashMap<>();

  public void decreaseQuantity(Long dishId, Integer quantity) {
    Integer currentQuantity = getQuantity(dishId);
    int dif = currentQuantity - quantity;
    if (dif < 0) {
      throw new RuntimeException("Not enough dishes");
    }
    dishQuantityMap.put(dishId, dif);
  }

  private Integer getQuantity(Dish dish) {
    Long id = dish.getId();
    dishQuantityMap.putIfAbsent(id, new Random().nextInt(20));
    return dishQuantityMap.get(id);
  }

  private Integer getQuantity(Long dishId) {
    dishQuantityMap.putIfAbsent(dishId, new Random().nextInt(20));
    return dishQuantityMap.get(dishId);
  }
}
