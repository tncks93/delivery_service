package com.delivery_service.owners.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery_service.owners.entity.Menu;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OwnerMenuRepositoryTest {

  @Autowired
  private OwnerMenuRepository repository;

  @Test
  @DisplayName("메뉴 추가")
  void addMenu() {
    //given
    Menu menu = createMenu();

    //when
    Menu savedMenu = repository.save(menu);

    //then
    assertThat(savedMenu.getId()).isNotNull();
    assertThat(savedMenu.getName()).isEqualTo(menu.getName());
    assertThat(savedMenu.getDescription()).isEqualTo(menu.getDescription());
    assertThat(savedMenu.getPrice()).isEqualTo(menu.getPrice());
    log.debug("savedMenu={}", savedMenu);

  }

  @Test
  @DisplayName("메뉴 수정")
  void updateMenu() {
    //given
    Menu savedMenu = repository.save(createMenu());

    //when
    savedMenu.setName("불고기 버거");
    savedMenu.setDescription("불고기 버거 입니다.");
    savedMenu.setPrice(7_000);

    //then
    Menu foundMenu = repository.findById(savedMenu.getId()).get();
    assertThat(foundMenu.getId()).isEqualTo(savedMenu.getId());
    assertThat(foundMenu.getName()).isEqualTo(savedMenu.getName());
    assertThat(foundMenu.getDescription()).isEqualTo(savedMenu.getDescription());
    assertThat(foundMenu.getPrice()).isEqualTo(savedMenu.getPrice());

    log.debug("savedMenu={}", savedMenu);

  }

  @Test
  @DisplayName("메뉴 판매 -> 품절 변경")
  void setMenuStatusNotOnSale() {
    //given
    Menu savedMenu = repository.save(createMenu());
    boolean isOnSale = false;

    //when
    savedMenu.setIsOnSale(isOnSale);

    //then
    Menu foundMenu = repository.findById(savedMenu.getId()).get();
    assertThat(foundMenu.getIsOnSale()).isEqualTo(isOnSale);
    log.debug("foundMenu={}", foundMenu);

  }

  @Test
  @DisplayName("메뉴 품절 -> 판매 변경")
  void setMenuStatusOnSale() {
    //given
    Menu menu = createMenu();
    menu.setIsOnSale(false);
    Menu savedMenu = repository.save(menu);
    boolean isOnSale = true;

    //when
    savedMenu.setIsOnSale(isOnSale);

    //then
    Menu foundMenu = repository.findById(savedMenu.getId()).get();
    assertThat(foundMenu.getIsOnSale()).isEqualTo(isOnSale);
    log.debug("foundMenu={}", foundMenu);

  }


  @Test
  @DisplayName("가게 전체 메뉴 조회")
  void findAllByShopId() {
    //given
    Menu menu1 = new Menu();
    menu1.setName("치즈버거");
    menu1.setDescription("치즈버거 입니다.");
    menu1.setPrice(5_000);
    menu1.setShopId(1);
    menu1.setIsOnSale(true);
    repository.save(menu1);

    Menu menu2 = new Menu();
    menu2.setName("불고기버거");
    menu2.setDescription("불고기버거 입니다.");
    menu2.setPrice(7_000);
    menu2.setShopId(1);
    menu2.setIsOnSale(true);
    repository.save(menu2);

    //when
    List<Menu> foundMenus = repository.findAllByShopId(1);

    //then
    assertThat(foundMenus.size()).isEqualTo(2);
    log.debug("foundMenus={}", foundMenus);
  }

  private Menu createMenu() {
    Menu menu = new Menu();
    menu.setName("치즈버거");
    menu.setDescription("치즈버거 입니다.");
    menu.setPrice(5_000);
    menu.setShopId(1);
    menu.setIsOnSale(true);

    return menu;
  }
}