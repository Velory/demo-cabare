package com.ua.cabare.controllers;

import com.ua.cabare.models.Department;
import com.ua.cabare.services.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

  @Autowired
  private DepartmentService departmentService;

  @RequestMapping(value = "get/all")
  public List<Department> getAll() {
    return departmentService.getAll();
  }

  @RequestMapping(value = "/add", method = RequestMethod.PUT)
  public void addDepartment(@RequestBody Department department) {
    departmentService.addDepartment(department);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public void updateDepartment(@RequestBody Department department) {
    departmentService.updateDepartment(department);
  }
}
