package com.delivery_service.controller.owner;

import com.delivery_service.annotation.User;
import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.dto.OrderNumDto;
import com.delivery_service.dto.OrderRequestResultDto;
import com.delivery_service.entity.Owner;
import com.delivery_service.enumeration.UserRole;
import com.delivery_service.service.OrderService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner/orders")
@AllArgsConstructor
public class OwnerOrderController {

  private final ExecutorService executorService;
  private final OrderService orderService;

  @PatchMapping("/respond")
  public CompletableFuture<ResponseEntity<CommonResponse<OrderNumDto>>> respondToRequestOrder(
      @User(role = UserRole.Owner) Owner owner, OrderRequestResultDto orderRequestResultDto) {

    return CompletableFuture.supplyAsync(() -> orderService.handleOrderRequestResult(owner,
            orderRequestResultDto.getOrderId(), orderRequestResultDto.getIsAccepted(),
            orderRequestResultDto.getCookingDurationMin()), executorService)
        .thenApply(OrderNumDto::new)
        .thenApply(CommonResponse::success)
        .thenApply((response) -> new ResponseEntity<>(response, HttpStatus.ACCEPTED));

  }

}
