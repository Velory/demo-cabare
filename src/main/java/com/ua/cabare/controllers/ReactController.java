package com.ua.cabare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReactController {

  @RequestMapping(value = {"/", "/react", "/start", "/bill", "/category_list", "/dish_list",
      "/opened_bills", "report", "shift"}, method = RequestMethod.GET)
  public String spa() {
    return "index";
  }
}
