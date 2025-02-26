package com.delivery_service.exception.advice;

import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.exception.LoginRequiredException;
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
