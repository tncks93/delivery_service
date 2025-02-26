package com.delivery_service.exception;

public class ShopNotFoundException extends RuntimeException {

  public ShopNotFoundException(Integer shopId) {
    super("Shop not found with id(" + shopId + ")");
  }

}
