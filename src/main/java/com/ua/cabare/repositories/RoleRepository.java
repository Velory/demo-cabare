package com.ua.cabare.repositories;

import com.ua.cabare.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findRoleById(Long id);

  Role findByName(String name);
}
