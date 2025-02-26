package com.delivery_service.exception;

public class OwnerNotFoundException extends RuntimeException {

  public OwnerNotFoundException(Integer ownerId) {
    super("Owner not found with id(" + ownerId + ")");
  }

}
