package com.ua.cabare.domain.calculation;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

public class RawMaterialDto {

  @NotBlank(message = "name is not specified")
  @JsonProperty("name")
  public String name;

  @NotBlank(message = "measure_unit shouldn't be empty")
  @JsonProperty("measure_unit")
  public String measureUnit;

  @Min(message = "threshold should be positive", value = 0)
  @JsonProperty("threshold")
  public int threshold;
}
