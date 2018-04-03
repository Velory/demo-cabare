package com.ua.cabare.repositories;

import com.ua.cabare.models.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByEmployee(String employee);

  Stream<VerificationToken> findAllByExpiryDateLessThan(LocalDate now);

  void deleteByExpiryDateLessThan(LocalDate now);

  @Modifying
  @Query("delete from VerificationToken t where t.expiryDate <=?1")
  void deleteAllByExpiredSince(LocalDate now);
}
