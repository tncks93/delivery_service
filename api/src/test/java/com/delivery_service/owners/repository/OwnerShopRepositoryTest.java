package com.delivery_service.owners.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery_service.entity.Shop;
import com.delivery_service.repository.owner.OwnerShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/before-test.sql")
class OwnerShopRepositoryTest {

  @Autowired
  private OwnerShopRepository repository;

  @Test
  @DisplayName("가게 등록")
  void save() {

    Shop shop = createShopObject();

    Shop savedShop = repository.save(shop);
    log.debug("savedShop={}", savedShop);

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

    Shop foundShop = repository.findById(savedShop.getId()).get();
    log.debug("foundShop={}", foundShop);

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

    savedShop.setName("롯데리아 버거");
    savedShop.setDescription("롯데리아 프랜차이즈");
    savedShop.setAddress("서울시 강서구 양천로 7길 14");
    savedShop.setIsOpen(false);
    savedShop.setCategory("패스트푸드");
    savedShop.setImage(null);
    log.debug("savedShop={}", savedShop);

    Shop updatedShop = repository.findById(savedShop.getId()).get();
    assertThat(updatedShop).isNotNull();
    assertThat(updatedShop.getId()).isEqualTo(savedShop.getId());
    assertThat(updatedShop.getName()).isEqualTo(savedShop.getName());
    assertThat(updatedShop.getDescription()).isEqualTo(savedShop.getDescription());
    assertThat(updatedShop.getAddress()).isEqualTo(savedShop.getAddress());
    assertThat(updatedShop.getCategory()).isEqualTo(savedShop.getCategory());
    assertThat(updatedShop.getImage()).isEqualTo(savedShop.getImage());
  }

  @Test
  @DisplayName("가게 CLOSE -> OPEN")
  void updateStatusCloseToOpen() {
    Shop savedShop = repository.save(createShopObject());
    boolean isOpen = true;

    savedShop.setIsOpen(isOpen);
    repository.save(savedShop);

    Shop foundShop = repository.findById(savedShop.getId()).get();
    log.debug("foundShop={}", foundShop);

    assertThat(foundShop.getIsOpen()).isTrue();
  }

  @Test
  @DisplayName("가게 OPEN -> CLOSE")
  void updateStatusOpenToClose() {
    Shop savedShop = repository.save(createShopObject());
    boolean isOpen = false;
    savedShop.setIsOpen(isOpen);
    repository.save(savedShop);

    Shop foundShop = repository.findById(savedShop.getId()).get();
    log.debug("foundShop={}", foundShop);

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