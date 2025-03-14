package com.delivery_service.consumer;

import com.delivery_service.dto.OrderDetail;
import com.delivery_service.entity.Order;
import com.delivery_service.entity.OrderMenu;
import com.delivery_service.event.RequestedOrderEvent;
import com.delivery_service.service.OrderCacheService;
import com.delivery_service.service.OrderMenuService;
import com.delivery_service.service.OrderService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RequestedOrderConsumer {

  private final OrderService orderService;
  private final OrderMenuService orderMenuService;
  private final OrderCacheService orderCacheService;

  @KafkaListener(topics = "order_requested", groupId = "delivery")
  @Transactional
  public void processRequestedOrder(RequestedOrderEvent event, Acknowledgment ack) {
    log.debug("requested order event={}", event);
    Order savedOrder = orderService.saveOrder(event.getOrder());
    List<OrderMenu> orderMenus = event.getOrderMenus();
    orderMenus.forEach(orderMenu -> {
      orderMenu.setOrderId(savedOrder.getId());
    });
    List<OrderMenu> savedOrderMenus = orderMenuService.saveOrderMenus(orderMenus);

    log.debug("order={}", savedOrder);
    savedOrderMenus.forEach(savedMenu -> log.debug("savedMenu={}", savedMenu));

    orderCacheService.saveOrderDetail(new OrderDetail(savedOrder, savedOrderMenus));

    ack.acknowledge();

  }

}
