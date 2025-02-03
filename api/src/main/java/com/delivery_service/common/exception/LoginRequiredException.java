package com.delivery_service.common.exception;

public class LoginRequiredException extends RuntimeException {

  public LoginRequiredException(String message) {
    super(message);
  }

}
