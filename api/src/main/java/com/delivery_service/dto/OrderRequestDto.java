package com.delivery_service.dto;

import com.delivery_service.entity.Order;
import com.delivery_service.entity.OrderMenu;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OrderRequestDto {

  private Integer shopId;
  private Integer totalPrice;
  private String address;
  private Boolean isContactless;
  private List<OrderMenuDto> orderMenus;

  public Order convertToEntity() {
    Order order = new Order();
    order.setShopId(shopId);
    order.setTotalPrice(totalPrice);
    order.setAddress(address);

    return order;
  }

  public List<OrderMenu> convertToOrderMenuEntities() {
    return orderMenus.stream().map(OrderMenuDto::convertToEntity)
        .collect(Collectors.toList());
  }

}
