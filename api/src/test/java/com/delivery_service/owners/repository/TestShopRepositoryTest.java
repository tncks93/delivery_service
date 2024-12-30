package com.delivery_service.owners.repository;

import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.entity.Shop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TestShopRepositoryTest {
    private TestShopRepository repository = new TestShopRepository();

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    @DisplayName("가게 등록 및 조회")
    void save() {
        int ownerId = 1;
        Shop shop = new Shop();
        shop.setName("프랭크 버거");
        shop.setDescription("버거 맛있음");
        shop.setAddress("서울시 강서구 양천로23길 9");
        shop.setIsOpen(false);
        shop.setCategory("패스트푸드");
        shop.setImage(null);

        repository.save(ownerId,shop);

        Shop foundShop = repository.findByOwnerId(ownerId).get();
        assertThat(foundShop).isEqualTo(shop);
    }

    @Test
    @DisplayName("가게 정보 수정")
    void update() {
        int ownerId = 1;
        Shop shop = new Shop();
        shop.setName("프랭크 버거");
        shop.setDescription("버거 맛있음");
        shop.setAddress("서울시 강서구 양천로23길 9");
        shop.setIsOpen(false);
        shop.setCategory("패스트푸드");
        shop.setImage(null);
        repository.save(ownerId,shop);
        int shopId = repository.findByOwnerId(ownerId).get().getId();

        Shop updateShop = new Shop();
        updateShop.setId(shopId);
        updateShop.setName("롯데리아 버거");
        updateShop.setDescription("롯데리아 프랜차이즈");
        updateShop.setAddress("서울시 강서구 양천로 7길 14");
        updateShop.setIsOpen(false);
        updateShop.setCategory("패스트푸드");
        updateShop.setImage(null);
        repository.update(ownerId,updateShop);

        Shop foundShop = repository.findByOwnerId(ownerId).get();
        assertThat(foundShop.getName()).isEqualTo(updateShop.getName());
        assertThat(foundShop.getDescription()).isEqualTo(updateShop.getDescription());
        assertThat(foundShop.getAddress()).isEqualTo(updateShop.getAddress());
        assertThat(foundShop.getCategory()).isEqualTo(updateShop.getCategory());
        assertThat(foundShop.getImage()).isEqualTo(updateShop.getImage());
    }

    @Test
    @DisplayName("가게 OPEN/CLOSE 상태 변경")
    void updateIsOpen() {
        int ownerId = 1;
        Shop shop = new Shop();
        shop.setName("프랭크 버거");
        shop.setDescription("버거 맛있음");
        shop.setAddress("서울시 강서구 양천로23길 9");
        shop.setIsOpen(false);
        shop.setCategory("패스트푸드");
        shop.setImage(null);
        repository.save(ownerId,shop);

        boolean isOpen = true;

        repository.updateIsOpen(ownerId,isOpen);

        Shop foundShop = repository.findByOwnerId(ownerId).get();
        assertThat(foundShop.getIsOpen()).isTrue();
    }





}