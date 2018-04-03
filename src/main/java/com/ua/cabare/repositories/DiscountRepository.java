package com.ua.cabare.repositories;

import com.ua.cabare.domain.Gender;
import com.ua.cabare.domain.Money;
import com.ua.cabare.models.Discount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

  Optional<Discount> findById(Long id);

  Optional<Discount> findByCardNumber(String discountCard);

  List<Discount> findByLastName(String lastName);

  List<Discount> findByGender(Gender gender);

  List<Discount> findAllByBirthdayBetween(LocalDate startDate, LocalDate endDate);

  List<Discount> findAllByEmittedBetween(LocalDate startDate, LocalDate endDate);

  List<Discount> findAllByTotalPaidBetween(Money from, Money to);

  List<Discount> findAllBySize(Integer size);

  @Query("select d from Discount d where d.activated = false")
  List<Discount> findAllBlocked();

  Discount save(Discount discount);
}
