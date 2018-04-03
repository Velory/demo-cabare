package com.ua.cabare.repositories;

import com.ua.cabare.models.Dish;
import com.ua.cabare.models.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  Optional<OrderItem> findById(Long id);

  Optional<OrderItem> findByOrderNumber(Long orderNumber);

  List<OrderItem> findByDish(Dish dish);
}
