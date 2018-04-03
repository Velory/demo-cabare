package com.ua.cabare.services;

import com.ua.cabare.domain.Utils;
import com.ua.cabare.domain.dish.DishCategoryDto;
import com.ua.cabare.exceptions.DishCategoryNotFoundException;
import com.ua.cabare.exceptions.DishCategoryNotSpecifiedException;
import com.ua.cabare.models.DishCategory;
import com.ua.cabare.models.Stock;
import com.ua.cabare.repositories.DishCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishCategoryService {

  @Autowired
  private DishCategoryRepository dishCategoryRepository;
  @Autowired
  private StockService stockService;

  public DishCategory findById(Long id) {
    if (id == null) {
      throw new DishCategoryNotSpecifiedException();
    }
    return dishCategoryRepository.findById(id)
        .orElseThrow(() -> new DishCategoryNotFoundException());
  }

  public DishCategory addNew(DishCategoryDto dishCategoryDto) {
    dishCategoryDto.setPhoto(null);
    DishCategory dishCategory = new DishCategory();
    dishCategory.setName(dishCategoryDto.getName());
    Long stockId = dishCategoryDto.getStockId();
    Stock stock = stockService.findById(stockId);
    dishCategory.setStock(stock);

    if (dishCategoryRepository.findByName(dishCategory.getName()).isPresent()) {
      throw new RuntimeException("such dish category has allready defined in db");
    }
    dishCategory.setId(null);
    return dishCategoryRepository.save(dishCategory);
  }

  public List<DishCategory> getCategories(Long stockId, Pageable pageable) {
    return dishCategoryRepository.streamAllPaged(stockId, pageable);
  }

  public DishCategory update(DishCategoryDto dishCategoryDto) {
    dishCategoryDto.setPhoto(null);
    Long id = dishCategoryDto.getId();
    DishCategory dishCategoryStored = findById(id);

    Optional<DishCategory> byName = dishCategoryRepository.findByName(dishCategoryDto.getName());
    if (byName.isPresent() && !byName.get().equals(dishCategoryStored)) {
      throw new RuntimeException("such dish category has allready defined in db");
    }
    DishCategory dishCategory = new DishCategory();
    dishCategory.setName(dishCategoryDto.getName());
    Long stockId = dishCategoryDto.getStockId();
    Stock stock = stockService.findById(stockId);
    dishCategory.setStock(stock);
    DishCategory dishCategoryUpdated = Utils.updateState(dishCategoryStored, dishCategory);
    return dishCategoryRepository.save(dishCategoryUpdated);
  }
}
