package com.delivery_service.service;

import com.delivery_service.dto.OrderDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderCacheService {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<String, String> redisTemplate;

  public void saveOrderDetail(OrderDetail orderDetail) {

    try {
      String key = getKey(orderDetail.getOrder().getId());
      redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(orderDetail));

    } catch (JsonProcessingException e) {
      //exception 처리?
      throw new RuntimeException(e);
    }
  }

  private String getKey(String orderId) {
    String key = "order:" + orderId;
    log.debug("order cache key={}", key);
    return key;
  }


}
