package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Shop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OwnerShopRepositoryTest {
    private OwnerShopRepository repository = new OwnerShopRepository();

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    @DisplayName("가게 등록")
    void save() {

        Shop shop = createShopObject();

        Shop savedShop = repository.save(shop);

        assertThat(savedShop.getId()).isNotNull();
        assertThat(savedShop.getName()).isEqualTo(shop.getName());
        assertThat(savedShop.getDescription()).isEqualTo(shop.getDescription());
        assertThat(savedShop.getAddress()).isEqualTo(shop.getAddress());
        assertThat(savedShop.getIsOpen()).isEqualTo(shop.getIsOpen());
        assertThat(savedShop.getCategory()).isEqualTo(shop.getCategory());
        assertThat(savedShop.getImage()).isEqualTo(shop.getImage());

    }

    @Test
    @DisplayName("가게 조회")
    void findByShopId() {
        Shop savedShop = repository.save(createShopObject());

        Shop foundShop = repository.findByShopId(savedShop.getId());

        assertThat(foundShop).isNotNull();
        assertThat(foundShop.getId()).isEqualTo(savedShop.getId());
        assertThat(foundShop.getName()).isEqualTo(savedShop.getName());
        assertThat(foundShop.getDescription()).isEqualTo(savedShop.getDescription());
        assertThat(foundShop.getAddress()).isEqualTo(savedShop.getAddress());
        assertThat(foundShop.getIsOpen()).isEqualTo(savedShop.getIsOpen());
        assertThat(foundShop.getCategory()).isEqualTo(savedShop.getCategory());
        assertThat(foundShop.getImage()).isEqualTo(savedShop.getImage());

    }

    @Test
    @DisplayName("가게 정보 수정")
    void update() {
        Shop savedShop = repository.save(createShopObject());

        Shop shop = new Shop();
        shop.setId(savedShop.getId());
        shop.setName("롯데리아 버거");
        shop.setDescription("롯데리아 프랜차이즈");
        shop.setAddress("서울시 강서구 양천로 7길 14");
        shop.setIsOpen(false);
        shop.setCategory("패스트푸드");
        shop.setImage(null);
        Shop updatedShop = repository.update(savedShop.getId(),shop);

        assertThat(updatedShop).isNotNull();
        assertThat(updatedShop.getId()).isEqualTo(savedShop.getId());
        assertThat(updatedShop.getName()).isEqualTo(shop.getName());
        assertThat(updatedShop.getDescription()).isEqualTo(shop.getDescription());
        assertThat(updatedShop.getAddress()).isEqualTo(shop.getAddress());
        assertThat(updatedShop.getCategory()).isEqualTo(shop.getCategory());
        assertThat(updatedShop.getImage()).isEqualTo(shop.getImage());
    }

    @Test
    @DisplayName("가게 CLOSE -> OPEN")
    void updateStatusCloseToOpen() {
        Shop savedShop = repository.save(createShopObject());
        boolean isOpen = true;

        repository.updateIsOpen(savedShop.getId(),isOpen);

        Shop foundShop = repository.findByShopId(savedShop.getId());
        assertThat(foundShop.getIsOpen()).isTrue();
    }

    @Test
    @DisplayName("가게 OPEN -> CLOSE")
    void updateStatusOpenToClose() {
        Shop savedShop = repository.save(createShopObject());
        boolean isOpen = false;

        repository.updateIsOpen(savedShop.getId(),isOpen);

        Shop foundShop = repository.findByShopId(savedShop.getId());
        assertThat(foundShop.getIsOpen()).isFalse();
    }

    Shop createShopObject() {
        Shop shop = new Shop();
        shop.setName("프랭크 버거");
        shop.setDescription("버거 맛있음");
        shop.setAddress("서울시 강서구 양천로23길 9");
        shop.setIsOpen(false);
        shop.setCategory("패스트푸드");
        shop.setImage(null);

        return shop;
    }




}