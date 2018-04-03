package com.ua.cabare.repositories;

import com.ua.cabare.models.StockActionReason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockActionReasonRepository extends JpaRepository<StockActionReason, Long> {

  Optional<StockActionReason> findById(Long reasonId);
}

