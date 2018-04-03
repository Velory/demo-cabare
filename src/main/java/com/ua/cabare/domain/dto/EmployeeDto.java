package com.ua.cabare.domain.dto;

import com.ua.cabare.models.Role;
import com.ua.cabare.validation.MatchPassword;
import com.ua.cabare.validation.ValidEmail;
import com.ua.cabare.validation.ValidPassword;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@MatchPassword
public class EmployeeDto {

  @NotBlank
  private String name;

  @NotNull
  private Role role;

  @ValidEmail
  private String email;

  @ValidPassword
  private String password;

  @NotBlank
  private String matchingPassword;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMatchingPassword() {
    return matchingPassword;
  }

  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }
}
