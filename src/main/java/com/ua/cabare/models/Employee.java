package com.ua.cabare.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee extends EntityManager<Long, Employee> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position_id")
  private Position position;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
  private Set<Bill> bills;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "employee_role",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id")
  private Department department;

  @Column(name = "start_day", columnDefinition = "date")
  private LocalDate startDay;

  @Column(name = "fired_day", columnDefinition = "date")
  private LocalDate firedDay;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "salary_id")
  private Salary salary;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "birthday", columnDefinition = "date")
  private LocalDate birthday;

  @Column(name = "phone")
  private String phone;

  @JsonIgnore
  @Column(name = "enabled")
  private Boolean enabled;

  public Employee() {
    this.enabled = false;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Set<Bill> getBills() {
    return bills;
  }

  public void setBills(Set<Bill> bills) {
    this.bills = bills;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public LocalDate getStartDay() {
    return startDay;
  }

  public void setStartDay(LocalDate startDay) {
    this.startDay = startDay;
  }

  public LocalDate getFiredDay() {
    return firedDay;
  }

  public void setFiredDay(LocalDate firedDay) {
    this.firedDay = firedDay;
  }

  public Salary getSalary() {
    return salary;
  }

  public void setSalary(Salary salary) {
    this.salary = salary;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }
}

