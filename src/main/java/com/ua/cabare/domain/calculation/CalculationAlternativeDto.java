package com.ua.cabare.domain.calculation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.models.CalculationAlternative;

public class CalculationAlternativeDto {

  @JsonProperty("raw_material_id")
  public Long rawMaterialId;
  @JsonProperty("quantity")
  public Double quantity;
  @JsonProperty("level")
  public Integer level;

  public CalculationAlternativeDto() {
  }

  public CalculationAlternativeDto(CalculationAlternative alternative) {
    this.rawMaterialId = alternative.getRawMaterial().getId();
    this.quantity = alternative.getQuantity();
    this.level = alternative.getLevel();
  }
}
