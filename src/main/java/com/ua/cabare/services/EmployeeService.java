package com.ua.cabare.services;

import com.ua.cabare.domain.dto.EmployeeDto;
import com.ua.cabare.domain.dto.LoggegEmployeesDto;
import com.ua.cabare.exceptions.EmployeeAlreadyExistException;
import com.ua.cabare.exceptions.EmployeeNotFoundException;
import com.ua.cabare.models.Employee;
import com.ua.cabare.models.Role;
import com.ua.cabare.models.VerificationToken;
import com.ua.cabare.repositories.EmployeeRepository;
import com.ua.cabare.repositories.VerificationTokenRepository;
import com.ua.cabare.event.ConfirmEmailEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private SessionRegistry sessionRegistry;

  private HttpServletRequest request;

  public EmployeeService(HttpServletRequest request) {
    this.request = request;
  }

  @Transactional
  public Employee registerNewEmployeeAccount(EmployeeDto employeeDto) {
    if (!isEmailExist(employeeDto.getEmail())) {
      throw new EmployeeAlreadyExistException(messageSource.getMessage("message.regError",
          null, request.getLocale()) + employeeDto.getEmail());
    }
    Employee employee = employeeRepository.save(getEmployeeFromDto(employeeDto));
    if (employee != null) {
      eventPublisher.publishEvent(new ConfirmEmailEvent(employee, request.getLocale(),
          getAppUrl(request), null));
    }
    return employee;
  }

  public Employee getEmployee(String verificationToken) {
    VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
    if (token != null) {
      return token.getEmployee();
    }
    return null;
  }

  public Employee getEmployeeByEmail(String email) {
    return employeeRepository.findByEmail(email);
  }

  @Transactional
  public void updateEmployee(Employee employee) {
    employeeRepository.save(employee);
  }


  private boolean isEmailExist(String email) {
    if (employeeRepository.findByEmail(email) != null) {
      return false;
    }
    return true;
  }

  private Employee getEmployeeFromDto(EmployeeDto employeeDto) {
    Employee employee = new Employee();
    employee.setName(employeeDto.getName());
    Set<Role> roles = new HashSet<>();
    roles.add(employeeDto.getRole());
    employee.setRoles(roles);
    employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
    employee.setEmail(employeeDto.getEmail());
    return employee;
  }

  public String getAppUrl(HttpServletRequest request) {
    return "http://" + request.getServerName() + ":"
        + request.getServerPort() + request.getContextPath();
  }

  public Employee getById(Long id) {
    return employeeRepository.findOne(id);
  }

  public List<LoggegEmployeesDto> getLoggedEmployeesFromSession() {
    List<LoggegEmployeesDto> employees = new ArrayList<>();
    for (Object obj: sessionRegistry.getAllPrincipals()) {
       User user = (User) obj;
       employees.add(new LoggegEmployeesDto(
           employeeRepository.findByEmail(
               user.getUsername()).getName()));
    }
    return employees;
  }

  public Employee getEmployeeById(Long id) {
    return employeeRepository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException());
  }

}
