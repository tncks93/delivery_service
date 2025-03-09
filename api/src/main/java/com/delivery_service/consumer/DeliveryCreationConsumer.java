package com.delivery_service.consumer;

import com.delivery_service.event.AcceptedOrderEvent;
import com.delivery_service.service.DeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DeliveryCreationConsumer {

  private final DeliveryService deliveryService;

  @KafkaListener(topics = "order_accepted", groupId = "delivery")
  public void createDelivery(AcceptedOrderEvent event, Acknowledgment ack) {
    //address 위경도로 변환(naver api?)
//    curl --location --request GET 'https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=분당구 불정로 6' \
//    --header 'x-ncp-apigw-api-key-id: {API Key ID}' \
//    --header 'x-ncp-apigw-api-key: {API Key}' \
//    --header 'Accept: application/json'
    //db저장
    //라이더매칭을 위한 event발행

  }

}
