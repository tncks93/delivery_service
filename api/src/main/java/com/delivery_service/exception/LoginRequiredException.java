package com.delivery_service.exception;

public class LoginRequiredException extends RuntimeException {

  public LoginRequiredException(String message) {
    super(message);
  }

}
