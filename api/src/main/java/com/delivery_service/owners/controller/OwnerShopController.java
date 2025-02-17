package com.delivery_service.owners.controller;

import com.delivery_service.common.ImageUrlProvider;
import com.delivery_service.common.annotation.User;
import com.delivery_service.common.enumeration.UserRole;
import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.owners.dto.ShopInfoDto;
import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.dto.ShopStatusDto;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.facade.OwnerShopFacade;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/owners/shops")
public class OwnerShopController {

  private final OwnerShopFacade ownerShopFacade;
  private final ExecutorService executorService;


  @PostMapping
  public CompletableFuture<ResponseEntity<CommonResponse<ShopInfoDto>>> addShop(
      @User(role = UserRole.Owner) Owner owner,
      @RequestBody ShopRegisterDto shopRegisterDto) {
    log.debug("thread={}", Thread.currentThread().getName());

    return CompletableFuture.supplyAsync(
            () -> ownerShopFacade.addShop(owner, shopRegisterDto.convertToEntity()), executorService)
        .thenApply(savedShop -> CommonResponse.success(ShopInfoDto.convertToDto(savedShop)))
        .thenApply(response -> new ResponseEntity<>(response, HttpStatus.CREATED));
  }

  @GetMapping
  public CompletableFuture<ResponseEntity<CommonResponse<ShopInfoDto>>> getShop(
      @User(role = UserRole.Owner) Owner owner) {

    return CompletableFuture.supplyAsync(() -> ownerShopFacade.getShop(owner), executorService)
        .thenApply(ShopInfoDto::convertToDto)
        .thenApply((shopInfoDto) -> {
          shopInfoDto.setImage(ImageUrlProvider.getPublicUrl(shopInfoDto.getImage()));
          return shopInfoDto;
        }).thenApply(CommonResponse::success)
        .thenApply(response -> new ResponseEntity<>(response, HttpStatus.OK));
  }


  @PutMapping
  public CompletableFuture<ResponseEntity<CommonResponse<ShopInfoDto>>> updateShop(
      @User(role = UserRole.Owner) Owner owner,
      @RequestBody ShopInfoDto shopInfoDto) {

    return CompletableFuture.supplyAsync(
            () -> ownerShopFacade.updateShop(owner, shopInfoDto.convertToEntity()), executorService)
        .thenApply(ShopInfoDto::convertToDto)
        .thenApply(CommonResponse::success)
        .thenApply(response -> new ResponseEntity<>(response, HttpStatus.OK));

  }

  @PatchMapping("/status")
  public CompletableFuture<ResponseEntity<CommonResponse<ShopStatusDto>>> updateShopStatus(
      @User(role = UserRole.Owner) Owner owner,
      @RequestBody ShopStatusDto shopStatusDto) {

    return CompletableFuture.supplyAsync(
            () -> ownerShopFacade.updateShopStatus(owner, shopStatusDto.getIsOpen()), executorService)
        .thenApply((isOpen) -> {
          shopStatusDto.setIsOpen(isOpen);
          return shopStatusDto;
        }).thenApply(CommonResponse::success)
        .thenApply(response -> new ResponseEntity<>(response, HttpStatus.OK));

  }
}
