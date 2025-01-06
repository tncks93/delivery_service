package com.delivery_service.owners.controller;

import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.owners.annotation.OwnerLogin;
import com.delivery_service.owners.dto.ShopInfoDto;
import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.dto.ShopStatusDto;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.service.OwnerShopService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/owners/shops")
public class OwnerShopController {

    private final OwnerShopService ownerShopService;

    @PostMapping
    public ResponseEntity<CommonResponse<ShopInfoDto>> addShop(@OwnerLogin Owner owner, @RequestBody ShopRegisterDto shopRegisterDto) {
        log.debug("owner = {}, shopRegisterDto = {} addShop in OwnerShopController", owner,shopRegisterDto);
        Shop savedShop = ownerShopService.addShop(owner, shopRegisterDto.convertToEntity());

        CommonResponse<ShopInfoDto> response = CommonResponse.success(ShopInfoDto.convertToDto(savedShop));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<ShopInfoDto>> getShop(@OwnerLogin Owner owner) {
        log.debug("owner = {} getShop in OwnerShopController", owner);
        Shop shop = ownerShopService.getShop(owner);
        log.debug("shop = {} getShop in OwnerShopController",shop);

        CommonResponse<ShopInfoDto> response = CommonResponse.success(ShopInfoDto.convertToDto(shop));

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<CommonResponse<ShopInfoDto>> updateShop(@OwnerLogin Owner owner, @RequestBody ShopInfoDto shopInfoDto) {
        log.debug("owner = {} shopInfoDto = {} updateShop in OwnerShopController", owner,shopInfoDto);
        Shop updatedShop = ownerShopService.updateShop(owner, shopInfoDto.convertToEntity());
        log.debug("updatedShop = {} updateShop in OwnerShopController",updatedShop);

        CommonResponse<ShopInfoDto> response = CommonResponse.success(ShopInfoDto.convertToDto(updatedShop));

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("/status")
    public ResponseEntity<CommonResponse<ShopStatusDto>> updateShopStatus(@OwnerLogin Owner owner, @RequestBody ShopStatusDto shopStatusDto) {
        log.debug("owner = {} shopStatusDto = {} updateShopStatus in OwnerShopController", owner,shopStatusDto);
        Boolean isOpen = ownerShopService.updateShopStatus(owner,shopStatusDto.getIsOpen());
        shopStatusDto.setIsOpen(isOpen);

        CommonResponse<ShopStatusDto> response = CommonResponse.success(shopStatusDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
