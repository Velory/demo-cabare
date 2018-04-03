package com.ua.cabare.services;

import com.ua.cabare.models.Department;
import com.ua.cabare.repositories.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

  @Autowired
  private DepartmentRepository departmentRepository;

  public List<Department> getAll() {
    return departmentRepository.findAll();
  }

  public Department addDepartment(Department department) {
    return departmentRepository.save(department);
  }

  public Department updateDepartment(Department department) {
    return departmentRepository.save(department);
  }
}
