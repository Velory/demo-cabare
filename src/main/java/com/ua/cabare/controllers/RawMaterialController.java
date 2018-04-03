package com.ua.cabare.controllers;

import com.ua.cabare.domain.calculation.RawMaterialDto;
import com.ua.cabare.models.RawMaterial;
import com.ua.cabare.services.RawMaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/raw_material")
public class RawMaterialController {

  @Autowired
  private RawMaterialService rawMaterialService;

  @RequestMapping(value = "/add", method = RequestMethod.PUT)
  public void add(@RequestBody @Valid RawMaterialDto rawMaterialDto) {
    rawMaterialService.add(rawMaterialDto);
  }

  @RequestMapping(value = "/get_all")
  public Page<RawMaterial> getAll(Pageable pageable) {
    return rawMaterialService.getAll(pageable);
  }
}
