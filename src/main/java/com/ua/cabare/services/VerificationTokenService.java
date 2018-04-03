package com.ua.cabare.services;

import com.ua.cabare.models.Employee;
import com.ua.cabare.repositories.EmployeeRepository;
import com.ua.cabare.repositories.VerificationTokenRepository;
import com.ua.cabare.models.VerificationToken;
import com.ua.cabare.exceptions.TokenNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class VerificationTokenService {

  private static final String TOKEN_INVALID = "invalidToken";
  private static final String TOKEN_EXPIRED = "expired";
  private static final String TOKEN_VALID = "valid";

  @Autowired
  private VerificationTokenRepository verificationTokenRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  public String validateVerificationToken(String token) {
    VerificationToken tokenFromDb = verificationTokenRepository.findByToken(token);
    if (tokenFromDb == null) {
      return TOKEN_INVALID;
    }

    Employee employee = tokenFromDb.getEmployee();
    Period period = Period.between(tokenFromDb.getExpiryDate(), LocalDate.now());
    if (period.getDays() >= 1) {
      verificationTokenRepository.delete(tokenFromDb);
      return TOKEN_EXPIRED;
    }

    employee.setEnabled(true);
    employeeRepository.save(employee);
    return TOKEN_VALID;
  }

  @Transactional
  public void createVerificationTokenForEmployee(Employee employee, String token) {
    VerificationToken newToken = new VerificationToken(token, employee);
    verificationTokenRepository.saveAndFlush(newToken);
  }

  @Transactional
  public VerificationToken generateNewVerificationToken(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    if (verificationToken != null) {
      verificationToken.setToken(getToken());
      verificationToken.setExpiryDate(LocalDate.now());
      verificationTokenRepository.saveAndFlush(verificationToken);
      return verificationToken;
    }
    throw new TokenNotFoundException();
  }

  public String getToken(){
    return UUID.randomUUID().toString();
  }
}
