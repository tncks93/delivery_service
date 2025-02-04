package com.delivery_service.common.advice;

import com.delivery_service.common.exception.MismatchedShopOwnerException;
import com.delivery_service.common.exception.OwnerNotFoundException;
import com.delivery_service.common.exception.ShopAlreadyExistsException;
import com.delivery_service.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OwnerExceptionAdvice {

  @ExceptionHandler(OwnerNotFoundException.class)
  public ResponseEntity<CommonResponse<Object>> handleOwnerNotFoundException(
      OwnerNotFoundException e) {

    return new ResponseEntity<>(CommonResponse.fail(e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MismatchedShopOwnerException.class)
  public ResponseEntity<CommonResponse<Object>> handleMismatchedShopOwnerException(
      MismatchedShopOwnerException e) {

    return new ResponseEntity<>(CommonResponse.fail(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ShopAlreadyExistsException.class)
  public ResponseEntity<CommonResponse<Object>> handleShopAlreadyExistsException(
      ShopAlreadyExistsException e) {

    return new ResponseEntity<>(CommonResponse.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

}
