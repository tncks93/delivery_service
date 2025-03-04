package com.delivery_service.service;

import com.delivery_service.entity.Customer;
import com.delivery_service.entity.Order;
import com.delivery_service.entity.OrderMenu;
import com.delivery_service.exception.InvalidOrderException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OngoingOrderService {

  public String requestOrder(Customer customer, Order order, List<OrderMenu> orderMenus,
      Integer deliveryFee, Boolean isContactless) {
    //메뉴 합계와 오더 가격 일치 확인
    validateOrderPrice(order, orderMenus, deliveryFee);

    order.setCustomerId(customer.getId());
    String orderNum = createOrderNum(order);
    order.setOrderNum(orderNum);

    //주문 요청 이벤트 발행

    return orderNum;
  }

  private void validateOrderPrice(Order order, List<OrderMenu> orderMenus, Integer deliveryFee) {
    int totalMenuPrice = orderMenus.stream().mapToInt(OrderMenu::getPrice)
        .sum();

    int calculatedPrice = totalMenuPrice + deliveryFee;

    if (order.getTotalPrice() != calculatedPrice) {
      throw new InvalidOrderException("invalid order request");
    }
  }

  private String createOrderNum(Order order) {
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

}
