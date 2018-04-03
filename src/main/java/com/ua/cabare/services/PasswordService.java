package com.ua.cabare.services;

import com.ua.cabare.models.Employee;
import com.ua.cabare.domain.dto.PasswordDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public PasswordService() {
  }

  public boolean isOldPasswordValid(PasswordDto passwordDto) {
    Employee employee = getEmployeeFromContext();
    return passwordEncoder.matches(passwordDto.getOldPassword(), employee.getPassword());
  }

  public void createNewPassword(PasswordDto passwordDto) {
    Employee employee = getEmployeeFromContext();
    employee.setPassword(passwordDto.getNewPassword());
    employeeService.updateEmployee(employee);
  }

  private Employee getEmployeeFromContext() {
    return employeeService.getEmployeeByEmail(((Employee) SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal())
        .getEmail());
  }

}
