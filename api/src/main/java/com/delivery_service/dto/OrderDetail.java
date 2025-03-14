package com.delivery_service.dto;

import com.delivery_service.entity.Delivery;
import com.delivery_service.entity.Order;
import com.delivery_service.entity.OrderMenu;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderDetail {

  private Order order;
  private List<OrderMenu> orderMenus;
  private Delivery delivery;

  public OrderDetail(Order order, List<OrderMenu> orderMenus) {
    this.order = order;
    this.orderMenus = orderMenus;
  }

}
