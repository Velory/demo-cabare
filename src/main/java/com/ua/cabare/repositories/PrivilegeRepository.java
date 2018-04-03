package com.ua.cabare.repositories;

import com.ua.cabare.models.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

  Privilege findByName(String name);

}
