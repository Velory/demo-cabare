package com.ua.cabare.services;

import com.ua.cabare.domain.calculation.RawMaterialDto;
import com.ua.cabare.exceptions.RawMaterialNotFoundException;
import com.ua.cabare.exceptions.RawMaterialNotSpecifiedException;
import com.ua.cabare.models.RawMaterial;
import com.ua.cabare.repositories.RawMaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RawMaterialService {

  @Autowired
  private RawMaterialRepository rawMaterialRepository;

  public void add(RawMaterialDto rawMaterialDto) {
    RawMaterial rawMaterial = new RawMaterial(rawMaterialDto);
    String name = rawMaterial.getName();
    Optional<RawMaterial> byName = rawMaterialRepository.findByName(name);
    if (byName.isPresent()) {
      throw new RuntimeException(name + " has allready exist");
    }
    rawMaterialRepository.save(rawMaterial);
  }

  public Page<RawMaterial> getAll(Pageable pageable) {
    return rawMaterialRepository.findAll(pageable);
  }

  public RawMaterial findRawMaterial(Long rawMaterialId) {
    if (rawMaterialId == null) {
      throw new RawMaterialNotSpecifiedException();
    }
    return rawMaterialRepository.findById(rawMaterialId)
        .orElseThrow(() -> new RawMaterialNotFoundException());
  }
}
