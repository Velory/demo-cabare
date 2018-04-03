package com.ua.cabare.domain.dto;

import com.ua.cabare.validation.MatchPassword;
import com.ua.cabare.validation.ValidPassword;

import org.hibernate.validator.constraints.NotBlank;

@MatchPassword
public class PasswordDto {

  @NotBlank
  private String oldPassword;

  @ValidPassword
  private String newPassword;

  @NotBlank
  private String matchingPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public String getMatchingPassword() {
    return matchingPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }
}
