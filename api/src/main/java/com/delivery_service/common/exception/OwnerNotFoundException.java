package com.delivery_service.common.exception;

public class OwnerNotFoundException extends RuntimeException {

  public OwnerNotFoundException(Integer ownerId) {
    super("Owner not found with id(" + ownerId + ")");
  }

}
