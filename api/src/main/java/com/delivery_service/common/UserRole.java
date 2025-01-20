package com.delivery_service.common;

public enum UserRole {
  Owner(com.delivery_service.owners.entity.Owner.class),
  Customer(null),
  Rider(null);

  private final Class clazz;

  UserRole(Class clazz) {
    this.clazz = clazz;
  }

  public Class getClazz() {
    return clazz;
  }
}
