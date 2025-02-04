package com.delivery_service.common.advice;

import com.delivery_service.common.exception.LoginRequiredException;
import com.delivery_service.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginUserExceptionAdvice {

  @ExceptionHandler(LoginRequiredException.class)
  public ResponseEntity<CommonResponse<Void>> handleLoginRequiredException(
      LoginRequiredException e) {

    return new ResponseEntity<>(CommonResponse.fail(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }

}
