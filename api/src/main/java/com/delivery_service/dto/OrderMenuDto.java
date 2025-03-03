package com.delivery_service.dto;

import com.delivery_service.entity.OrderMenu;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderMenuDto {

  private String name;
  private Integer price;

  public OrderMenu convertToEntity() {
    OrderMenu orderMenu = new OrderMenu();
    orderMenu.setName(name);
    orderMenu.setPrice(price);
    return orderMenu;
  }
}
