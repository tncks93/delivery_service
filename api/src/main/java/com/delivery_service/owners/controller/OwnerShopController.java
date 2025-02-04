package com.delivery_service.owners.controller;

import com.delivery_service.common.UserRole;
import com.delivery_service.common.annotation.User;
import com.delivery_service.common.dto.ImageUploadReqDto;
import com.delivery_service.common.dto.ImageUrlDto;
import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.owners.dto.ShopInfoDto;
import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.dto.ShopStatusDto;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.facade.OwnerShopFacade;
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


  @PostMapping
  public ResponseEntity<CommonResponse<ShopInfoDto>> addShop(
      @User(role = UserRole.Owner) Owner owner,
      @RequestBody ShopRegisterDto shopRegisterDto) {
    log.debug("owner = {}, shopRegisterDto = {}", owner, shopRegisterDto);
    Shop savedShop = ownerShopFacade.addShop(owner, shopRegisterDto.convertToEntity());

    CommonResponse<ShopInfoDto> response = CommonResponse.success(
        ShopInfoDto.convertToDto(savedShop));

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<CommonResponse<ShopInfoDto>> getShop(
      @User(role = UserRole.Owner) Owner owner) {
    log.debug("owner = {}", owner);
    Shop shop = ownerShopFacade.getShop(owner);
    log.debug("shop = {}", shop);

    CommonResponse<ShopInfoDto> response = CommonResponse.success(
        ShopInfoDto.convertToDto(shop));

    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @PutMapping
  public ResponseEntity<CommonResponse<ShopInfoDto>> updateShop(
      @User(role = UserRole.Owner) Owner owner,
      @RequestBody ShopInfoDto shopInfoDto) {
    log.debug("owner = {} shopInfoDto = {}", owner, shopInfoDto);
    Shop updatedShop = ownerShopFacade.updateShop(owner, shopInfoDto.convertToEntity());
    log.debug("updatedShop = {}", updatedShop);

    CommonResponse<ShopInfoDto> response = CommonResponse.success(
        ShopInfoDto.convertToDto(updatedShop));

    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @PatchMapping("/status")
  public ResponseEntity<CommonResponse<ShopStatusDto>> updateShopStatus(
      @User(role = UserRole.Owner) Owner owner,
      @RequestBody ShopStatusDto shopStatusDto) {
    log.debug("owner = {} shopStatusDto = {}", owner,
        shopStatusDto);
    Boolean isOpen = ownerShopFacade.updateShopStatus(owner, shopStatusDto.getIsOpen());
    shopStatusDto.setIsOpen(isOpen);

    CommonResponse<ShopStatusDto> response = CommonResponse.success(shopStatusDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/image-url")
  public ResponseEntity<CommonResponse<ImageUrlDto>> getShopImageUrl(
      @User(role = UserRole.Owner) Owner owner, @RequestBody ImageUploadReqDto imageUploadReqDto) {
    String shopImageUploadUrl = ownerShopFacade.getShopImageUploadUrl(
        imageUploadReqDto.getOriginalImageName());
    String shopImageDownloadUrl = ownerShopFacade.getShopImageDownloadUrl(shopImageUploadUrl);

    ImageUrlDto imageUrlDto = new ImageUrlDto(shopImageUploadUrl, shopImageDownloadUrl);

    CommonResponse<ImageUrlDto> response = CommonResponse.success(imageUrlDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
