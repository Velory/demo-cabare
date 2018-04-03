package com.ua.cabare.repositories;

import com.ua.cabare.models.RawMaterial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

  Optional<RawMaterial> findById(Long id);

  Page<RawMaterial> findAll(Pageable pageable);

  Optional<RawMaterial> findByName(String name);
}
