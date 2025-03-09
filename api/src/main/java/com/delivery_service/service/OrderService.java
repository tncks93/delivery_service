package com.delivery_service.service;

import com.delivery_service.entity.Customer;
import com.delivery_service.entity.Order;
import com.delivery_service.entity.OrderMenu;
import com.delivery_service.entity.Owner;
import com.delivery_service.enumeration.OrderStatus;
import com.delivery_service.event.AcceptedOrderEvent;
import com.delivery_service.event.RequestedOrderEvent;
import com.delivery_service.exception.InvalidOrderException;
import com.delivery_service.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

  //service 나누자
  private final OrderRepository orderRepository;
  private final OrderCacheService orderCacheService;
  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public String requestOrder(Customer customer, Order order, List<OrderMenu> orderMenus) {
    //메뉴 합계와 오더 가격 일치 확인
    validateOrderPrice(order, orderMenus);

    String orderNum = createOrderNum(order);

    order.setCustomerId(customer.getId());
    order.setId(orderNum);
    order.setStatus(OrderStatus.REQUESTING.getStatus());

    //주문 요청 이벤트 발행
    kafkaTemplate.send("order_requested", new RequestedOrderEvent(order, orderMenus));
    return orderNum;
  }

  public String handleOrderRequestResult(Owner owner, String orderId, Boolean isAccepted,
      Integer cookingDurationMin) {
    //custom exception 추가
    Order order = orderRepository.findById(orderId)
        .filter(savedOrder -> savedOrder.getShopId().equals(owner.getShopId()))
        .orElseThrow(() -> new InvalidOrderException("ShopId does not match"));

    if (isAccepted) {
      //오더 수락시
      order.setStatus(OrderStatus.COOKING.getStatus());
      LocalDateTime estimationCookingCompletionTime = calculateCookingCompleteTime(
          cookingDurationMin);

      orderCacheService.updateOrderCacheStatus(order);

      kafkaTemplate.send("order_accepted",
          new AcceptedOrderEvent(order, estimationCookingCompletionTime));

    } else {
      //오더 거절시
      order.setStatus(OrderStatus.CANCELED.getStatus());
      //캐시를 지워야하나?
      //취소 이벤트 처리는? 알림?
    }

    return order.getId();

  }

  public Order saveOrder(Order order) {
    return orderRepository.save(order);
  }

  private void validateOrderPrice(Order order, List<OrderMenu> orderMenus) {
    int totalMenuPrice = orderMenus.stream().mapToInt(OrderMenu::getPrice)
        .sum();

    int calculatedPrice = totalMenuPrice + order.getDeliveryFee();

    if (order.getTotalPrice() != calculatedPrice) {
      throw new InvalidOrderException("invalid order request");
    }
  }

  public String createOrderNum(Order order) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int randomLength = 6;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMdd");
    String date = dateFormat.format(LocalDate.now(ZoneId.of("Asia/Seoul")));
    StringBuilder orderNum = new StringBuilder();
    orderNum.append(date);
    orderNum.append(order.getShopId());
    Random random = new Random();
    for (int i = 0; i < randomLength; i++) {
      orderNum.append(characters.charAt(random.nextInt(characters.length())));
    }
    log.debug("orderNum={}", orderNum);
    return orderNum.toString();
  }

  public LocalDateTime calculateCookingCompleteTime(int cookingDurationMin) {
    LocalDateTime now = LocalDateTime.now();
    return now.plusMinutes(cookingDurationMin);
  }


}
