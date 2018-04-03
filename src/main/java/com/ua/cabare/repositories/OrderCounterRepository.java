package com.ua.cabare.repositories;

import com.ua.cabare.models.OrderCounter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderCounterRepository extends CrudRepository<OrderCounter, Long> {

  Optional<OrderCounter> findById(Long id);
}
