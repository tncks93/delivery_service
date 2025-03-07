package com.delivery_service.enumeration;

public enum OrderStatus {
  REQUESTING("주문 요청"),
  CANCELED("주문 취소"),
  COOKING("조리중"),
  COOKING_COMPLETE("조리 완료"),
  ON_DELIVERY("배달중"),
  DELIVERY_COMPLETE("배달 완료");

  private final String status;

  OrderStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
