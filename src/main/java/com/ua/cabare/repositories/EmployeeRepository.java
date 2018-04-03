package com.ua.cabare.repositories;

import com.ua.cabare.models.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findById(Long id);

  Optional<Employee> findByLogin(String login);

  Employee findByEmail(String email);

}
