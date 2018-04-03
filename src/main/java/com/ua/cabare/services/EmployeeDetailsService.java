package com.ua.cabare.services;

import com.ua.cabare.exceptions.EmployeeNotFoundException;
import com.ua.cabare.models.Employee;
import com.ua.cabare.models.Role;
import com.ua.cabare.repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeDetailsService implements UserDetailsService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Employee employee = employeeRepository.findByEmail(email);
    try {
      if (employee == null) {
        throw new EmployeeNotFoundException("No user found with email: " + email);
      }
      /*return new User(employee.getEmail(), employee.getPassword(), true, true,
          true, true, getAuthorities(employee.getRoles()));
    */
      return new User(employee.getEmail(), employee.getPassword(), true, true,
          true, true, getGrantedAuthorities(employee.getRoles()));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }

  //functionality for privileges

  /*private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  private List<String> getPrivileges(Set<Role> roles) {
    List<String> privileges = roles.stream()
        .flatMap(role -> role.getPrivileges().stream()
            .map(Privilege::getName))
        .collect(Collectors.toList());
    return privileges;
  }*/

  /*private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> grantedAuthorities = privileges.stream()
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return grantedAuthorities;
  }*/

}
