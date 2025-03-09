package com.delivery_service.consumer;

import com.delivery_service.event.AcceptedOrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DeliveryCreationConsumer {

  @KafkaListener(topics = "order_accepted", groupId = "delivery")
  public void createDelivery(AcceptedOrderEvent event, Acknowledgment ack) {

  }

}
