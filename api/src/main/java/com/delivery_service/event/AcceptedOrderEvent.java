package com.delivery_service.event;

import com.delivery_service.entity.Order;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AcceptedOrderEvent {

  private Order order;
  private LocalDateTime estimationCookingCompletionTime;


}
