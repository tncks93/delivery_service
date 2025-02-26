package com.delivery_service.service;

import com.delivery_service.dto.LoginUserInfo;
import com.delivery_service.enumeration.UserRole;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoginUserInfoCacheService {

  private final RedisTemplate<String, String> redisTemplate;

  public <T> LoginUserInfo<T> getLoginUserInfo(String token, UserRole userRole) {
    String key = getKey(userRole, token);
    String data = redisTemplate.opsForValue().get(key);
    if (data == null) {
      return null;
    }

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JavaType javaType = objectMapper.getTypeFactory()
          .constructParametricType(LoginUserInfo.class, userRole.getClazz());
      return objectMapper.readValue(data, javaType);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public <T> void saveLoginUserInfo(String token, LoginUserInfo<T> user) {
    String key = getKey(user.getLoginUser().getRole(), token);
    try {
      log.debug("key={}", key);
      ObjectMapper objectMapper = new ObjectMapper();
      String data = objectMapper.writeValueAsString(user);
      redisTemplate.opsForValue().set(key, data);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }


  }

  private String getKey(UserRole type, String token) {
    String key = type.name().toLowerCase() + ":" + token;
    log.debug("setKeyPrefix key={}", key);
    return key;
  }


}
