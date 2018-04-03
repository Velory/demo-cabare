package com.ua.cabare.models;

import org.hibernate.Hibernate;

import java.io.Serializable;

public abstract class EntityManager<K extends Comparable<K> & Serializable, E extends EntityManager<K, ?>>
    implements Serializable, Comparable<E> {

  public abstract K getId();

  public abstract void setId(K id);

  public boolean isNew() {
    return getId() == null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (Hibernate.getClass(object) != Hibernate.getClass(this)) {
      return false;
    }

    EntityManager<K, E> that = (EntityManager<K, E>) object;

    K id = that.getId();

    return id != null && id.equals(that.getId());
  }

  @Override
  public int hashCode() {
    int hash = 7;

    K id = getId();
    hash = 31 * hash + ((id == null) ? 0 : id.hashCode());

    return hash;
  }

  public int compareTo(E obj) {
    if (this == obj) {
      return 0;
    }
    return this.getId().compareTo(obj.getId());
  }

  @Override
  public String toString() {
    return Hibernate.getClass(this).getSimpleName()
        + ": "
        + getId()
        + "-"
        + super.toString();
  }
}
