package com.ua.cabare.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends EntityManager<Long, Role> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "role_privilege", joinColumns = @JoinColumn(name = "role_id"),
      foreignKey = @ForeignKey(name = "FK_RP_ROLE_ID"),
      inverseJoinColumns = @JoinColumn(name = "privilege_id"),
      inverseForeignKey = @ForeignKey(name = "FK_RP_PRIVILEGE_ID"))
  private Set<Privilege> privileges;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
  private Set<Employee> employees;

  public Role() {
  }

  public Role(String name) {
    super();
    this.name = name;
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

  public Set<Privilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Set<Privilege> privileges) {
    this.privileges = privileges;
  }
}
