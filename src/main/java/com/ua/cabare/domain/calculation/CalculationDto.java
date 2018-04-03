package com.ua.cabare.domain.calculation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.models.Calculation;
import com.ua.cabare.validation.MinDouble;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CalculationDto {

  @Min(value = 1, message = "raw_material_id should be positive")
  @JsonProperty("raw_material_id")
  public Long rawMaterialId;
  @JsonProperty("raw_material_name")
  public String rawMaterialName;
  @NotNull(message = "quantity should be specified")
  @MinDouble(message = "quantity should be positive", value = 0)
  @JsonProperty("quantity")
  public Double quantity;
  @JsonProperty("measure_unit")
  public String measureUnit;
  @JsonProperty("alternatives")
  public List<CalculationAlternativeDto> alternativeDtos;

  public CalculationDto() {
  }

  public CalculationDto(Calculation calculation) {
    this.rawMaterialId = calculation.getRawMaterial().getId();
    this.rawMaterialName = calculation.getRawMaterial().getName();
    this.measureUnit = calculation.getRawMaterial().getMeasureUnit();
    this.quantity = calculation.getQuantity();
    this.alternativeDtos = calculation.getCalculationAlternativeList().stream()
        .map(alternative -> new CalculationAlternativeDto(alternative))
        .collect(Collectors.toList());
  }
}
