package com.ua.cabare.services;

import com.ua.cabare.models.Role;
import com.ua.cabare.models.Privilege;
import com.ua.cabare.repositories.PrivilegeRepository;
import com.ua.cabare.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class RolesAndPermissionsDataService implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadyConfigured = false;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PrivilegeRepository privilegeRepository;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (alreadyConfigured) {
      return;
    }

    Privilege viewPrivilege = createPrivilege("VIEW");
    Privilege readPrivilege = createPrivilege("READ");
    Privilege createPrivilege = createPrivilege("CREATE");
    Privilege editPrivilege = createPrivilege("EDIT");
    Privilege deletePrivilege = createPrivilege("DELETE");

    List<Privilege> adminPrivileges = Arrays
        .asList(viewPrivilege, readPrivilege, createPrivilege, editPrivilege, deletePrivilege);
    List<Privilege> ownerPrivileges = Arrays.asList(viewPrivilege, readPrivilege);
    List<Privilege> managerPrivileges = Arrays.asList(readPrivilege, editPrivilege);
    List<Privilege> waiterPrivileges = Arrays.asList(readPrivilege, editPrivilege);

    createRole("ROLE_ADMIN", adminPrivileges);
    createRole("ROLE_OWNER", ownerPrivileges);
    createRole("ROLE_MANAGER", managerPrivileges);
    createRole("ROLE_WAITER", waiterPrivileges);

    alreadyConfigured = true;
  }

  private Privilege createPrivilege(String name) {
    Privilege privilege = privilegeRepository.findByName(name);
    if (privilege == null) {
      privilege = new Privilege(name);
      privilegeRepository.save(privilege);
    }
    return privilege;
  }

  private Role createRole(String name, List<Privilege> privileges) {
    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role(name);
      role.setPrivileges(new HashSet<>(privileges));
      roleRepository.save(role);
    }
    return role;
  }
}
