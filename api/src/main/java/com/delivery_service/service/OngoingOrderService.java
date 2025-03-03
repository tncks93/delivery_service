package com.delivery_service.service;

import com.delivery_service.entity.Customer;
import com.delivery_service.entity.Order;
import com.delivery_service.entity.OrderMenu;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OngoingOrderService {

  public String requestOrder(Customer customer, Order order, List<OrderMenu> orderMenus,
      Boolean isContactless) {
    //TODO: order 검증
    String orderNum = createOrderNum();
    order.setOrderNum(orderNum);

    //주문 요청 이벤트 발행
    return null;
  }

  private String createOrderNum() {
    //다른 방법 고민
    return UUID.randomUUID().toString();
  }

}
