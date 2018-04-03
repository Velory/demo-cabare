package com.ua.cabare.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;

@Component
@RequestScope
public class Response extends HashMap<String, Object> {

  public static final String BILL = "bill";
  public static final String BILL_LIST = "bill_list";
  public static final String STATUS = "status";
  public static final String DISCOUNT_CARD = "discount_card";
  public static final String DISCOUNT_SIZE = "discount_size";
  public static final String DEPARTMENTS = "departments";
  public static final String DEPARTMENT = "department";
  public static final String BILL_PRICE = "bill_price";
  public static final String DISH = "dish";
  public static final String DISH_LIST = "dish_list";
  public static final String DISH_CATEGORY = "dish_category";
  public static final String DISH_CATEGORY_LIST = "dish_category_list";
  public static final String ORDER_LIST = "ORDER_LIST";
  public static final String RAW_MATERIAL_PAGE = "RAW_MATERIAL_PAGE";
  public static final String CALCULATIONS = "CALCULATIONS";


  public Response() {
    put(STATUS, "ok");
  }
}
