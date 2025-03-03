package com.delivery_service.controller;

import com.delivery_service.annotation.User;
import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.dto.OrderNumDto;
import com.delivery_service.dto.OrderRequestDto;
import com.delivery_service.entity.Customer;
import com.delivery_service.enumeration.UserRole;
import com.delivery_service.service.OngoingOrderService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

  private final ExecutorService executorService;
  private final OngoingOrderService ongoingOrderService;

  @PostMapping("/request")
  public CompletableFuture<ResponseEntity<CommonResponse<OrderNumDto>>> requestOrder(
      @User(role = UserRole.Customer) Customer customer, @RequestBody
      OrderRequestDto orderRequestDto) {

    return CompletableFuture.supplyAsync(
            () -> ongoingOrderService.requestOrder(customer, orderRequestDto.convertToEntity(),
                orderRequestDto.convertToOrderMenuEntities(),
                orderRequestDto.getIsContactless()), executorService)
        .thenApply(OrderNumDto::new)
        .thenApply(CommonResponse::success)
        .thenApply(response -> new ResponseEntity<>(response, HttpStatus.ACCEPTED));

  }

}
