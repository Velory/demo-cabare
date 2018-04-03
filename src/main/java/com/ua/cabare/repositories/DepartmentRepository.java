package com.ua.cabare.repositories;

import com.ua.cabare.models.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

  Optional<Department> getById(Long id);

  Optional<Department> getByName(String departmentName);
}
