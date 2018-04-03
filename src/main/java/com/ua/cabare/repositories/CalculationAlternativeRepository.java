package com.ua.cabare.repositories;

import com.ua.cabare.models.CalculationAlternative;
import com.ua.cabare.models.RawMaterial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalculationAlternativeRepository extends
    JpaRepository<CalculationAlternative, Long> {

  Optional<CalculationAlternative> getById(Long id);

  @Query("select ca from CalculationAlternative ca where ca.rawMaterial = ?1 and ca.calculation.isArchived = false")
  List<CalculationAlternative> getActualByRawMaterial(RawMaterial rawMaterial);
}
