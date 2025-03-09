package com.delivery_service.dto;

import lombok.Getter;

@Getter
public class OrderRequestResultDto {

  private String orderId;
  private Boolean isAccepted;
  private Integer cookingDurationMin;

}
