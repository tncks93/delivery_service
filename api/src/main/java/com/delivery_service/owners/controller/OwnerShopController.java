package com.delivery_service.owners.controller;

import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.dto.ShopInfoDto;
import com.delivery_service.owners.dto.ShopStatusDto;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/owners/shops")
public class OwnerShopController {

    private final ShopRepository shopRepository;

    public OwnerShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @PostMapping
    public ResponseEntity<String> addShop(@SessionAttribute(name = "ownerId", required = false) Integer ownerId, @RequestBody ShopRegisterDto shopRegisterDto) {
        log.info("ownerId in addShop = {}", ownerId);
        Shop shop = shopRegisterDto.toShop();
        log.info("addShop: shop={}",shop);
        shopRepository.save(ownerId, shop);

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ShopInfoDto> getShop(@SessionAttribute(name = "ownerId", required = false) Integer ownerId) {
        log.info("ownerId in getShop = {}", ownerId);
        Optional<Shop> optionalShop = shopRepository.findByOwnerId(ownerId);
        Shop shop = optionalShop.get();
        log.info("getShop: shop={}",shop);

        ShopInfoDto shopInfoDto = new ShopInfoDto().convertToDto(shop);
        return new ResponseEntity<>(shopInfoDto, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<String> updateShop(@SessionAttribute(name = "ownerId", required = false) Integer ownerId, @RequestBody ShopInfoDto shopInfoDto) {

        shopRepository.update(ownerId,shopInfoDto.convertToEntity());

        return new ResponseEntity<>("UPDATED", HttpStatus.OK);

    }

    @PatchMapping("/status")
    public ResponseEntity<String> updateShopStatus(@SessionAttribute(name = "ownerId", required = false) Integer ownerId, @RequestBody ShopStatusDto shopStatusDto) {
        log.info("shopStatus={}",shopStatusDto.toString());
        shopRepository.updateIsOpen(ownerId, shopStatusDto.getIsOpen());

        return new ResponseEntity<>("UPDATED", HttpStatus.OK);
    }



}
