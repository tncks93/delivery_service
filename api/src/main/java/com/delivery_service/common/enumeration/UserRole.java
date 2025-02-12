package com.delivery_service.common.enumeration;

public enum UserRole {
  Owner(com.delivery_service.owners.entity.Owner.class),
  Customer(com.delivery_service.customers.entity.Customer.class),
  Rider(com.delivery_service.riders.entity.Rider.class);

  private final Class clazz;

  UserRole(Class clazz) {
    this.clazz = clazz;
  }

  public Class getClazz() {
    return clazz;
  }
}
