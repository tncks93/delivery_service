package com.delivery_service.enumeration;

public enum UserRole {
  Owner(com.delivery_service.entity.Owner.class),
  Customer(com.delivery_service.entity.Customer.class),
  Rider(com.delivery_service.entity.Rider.class);

  private final Class clazz;

  UserRole(Class clazz) {
    this.clazz = clazz;
  }

  public Class getClazz() {
    return clazz;
  }
}
