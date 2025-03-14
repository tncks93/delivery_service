package com.delivery_service.event;

import com.delivery_service.entity.Order;
import com.delivery_service.entity.OrderMenu;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RequestedOrderEvent {

  Order order;
  List<OrderMenu> orderMenus;

}
