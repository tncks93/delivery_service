package com.delivery_service.common.advice;

import com.delivery_service.common.exception.ShopNotFoundException;
import com.delivery_service.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ShopExceptionAdvice {

  @ExceptionHandler(ShopNotFoundException.class)
  public ResponseEntity<CommonResponse<Object>> handleShopNotFoundException(
      ShopNotFoundException e) {

    return new ResponseEntity<>(CommonResponse.fail(e.getMessage()), HttpStatus.NOT_FOUND);
  }

}
