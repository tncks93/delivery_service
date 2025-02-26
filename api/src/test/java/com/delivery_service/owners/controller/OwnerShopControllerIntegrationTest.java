package com.delivery_service.owners.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.dto.ShopInfoDto;
import com.delivery_service.dto.ShopRegisterDto;
import com.delivery_service.dto.ShopStatusDto;
import com.delivery_service.enumeration.UserRole;
import com.delivery_service.facade.LoginUserInfoFacade;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@Sql(scripts = "/before-test.sql")
class OwnerShopControllerIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private LoginUserInfoFacade loginUserInfoFacade;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @AfterEach
  void afterEach() {
    redisTemplate.getConnectionFactory().getConnection().flushDb();

  }

  String setLoginInfo() {
    String token = UUID.randomUUID().toString();
    log.debug("token={}", token);
    UserRole role = UserRole.Owner;
    loginUserInfoFacade.saveLoginUserInfo(role, token, role.getClazz(), 1);
    return token;
  }

  @Test
  @DisplayName("Owner의 가게 등록 API")
  void addShop() {

    ShopRegisterDto shopRegisterDto = new ShopRegisterDto();
    shopRegisterDto.setName("프랭크 버거");
    shopRegisterDto.setDescription("버거 맛있음");
    shopRegisterDto.setCategory("패스트푸드");
    shopRegisterDto.setAddress("서울시 강서구 양천로23길 9");
    shopRegisterDto.setImage("image.jpg");

    String token = setLoginInfo();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<ShopRegisterDto> addShopRequest = new HttpEntity<>(shopRegisterDto, headers);
    ResponseEntity<CommonResponse<ShopInfoDto>> addShopResult = testRestTemplate
        .exchange("/owners/shops", HttpMethod.POST, addShopRequest,
            new ParameterizedTypeReference<CommonResponse<ShopInfoDto>>() {
            });

    assertThat(addShopResult.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    CommonResponse<ShopInfoDto> body = addShopResult.getBody();
    assertThat(body.getStatus()).isEqualTo("success");
    ShopInfoDto addedShopInfoDto = body.getData();
    assertThat(addedShopInfoDto.getId()).isNotNull();
    assertThat(addedShopInfoDto.getName()).isEqualTo(shopRegisterDto.getName());
    assertThat(addedShopInfoDto.getDescription()).isEqualTo(shopRegisterDto.getDescription());
    assertThat(addedShopInfoDto.getCategory()).isEqualTo(shopRegisterDto.getCategory());
    assertThat(addedShopInfoDto.getAddress()).isEqualTo(shopRegisterDto.getAddress());
    assertThat(addedShopInfoDto.getIsOpen()).isFalse();
  }

  @Test
  @DisplayName("Owner의 가게 조회 API")
  void getShop() {
    ShopInfoDto addedShopInfoDto = getAddedShopInfoDto();

    String token = setLoginInfo();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<Void> getShopRequest = new HttpEntity(null, headers);
    ResponseEntity<CommonResponse<ShopInfoDto>> getShopResult = testRestTemplate
        .exchange("/owners/shops", HttpMethod.GET, getShopRequest,
            new ParameterizedTypeReference<CommonResponse<ShopInfoDto>>() {
            });

    assertThat(getShopResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    CommonResponse<ShopInfoDto> body = getShopResult.getBody();
    assertThat(body.getStatus()).isEqualTo("success");
    ShopInfoDto shopInfoDto = body.getData();
    assertThat(shopInfoDto.getId()).isEqualTo(addedShopInfoDto.getId());
    assertThat(shopInfoDto.getName()).isEqualTo(addedShopInfoDto.getName());
    assertThat(shopInfoDto.getDescription()).isEqualTo(addedShopInfoDto.getDescription());
    assertThat(shopInfoDto.getCategory()).isEqualTo(addedShopInfoDto.getCategory());
    assertThat(shopInfoDto.getAddress()).isEqualTo(addedShopInfoDto.getAddress());
    assertThat(shopInfoDto.getIsOpen()).isEqualTo(addedShopInfoDto.getIsOpen());
    if (shopInfoDto.getImage() != null) {
      log.debug("image={}", shopInfoDto.getImage());
    }

  }

  @Test
  @DisplayName("Owner의 가게 수정 API")
  void updateShop() {

    ShopInfoDto addedShopInfoDto = getAddedShopInfoDto();

    ShopInfoDto shopInfoDtoToUpdate = new ShopInfoDto();
    shopInfoDtoToUpdate.setId(addedShopInfoDto.getId());
    shopInfoDtoToUpdate.setName("롯데리아 버거");
    shopInfoDtoToUpdate.setDescription("롯데리아 프랜차이즈");
    shopInfoDtoToUpdate.setAddress("서울시 강서구 양천로 7길 14");
    shopInfoDtoToUpdate.setIsOpen(false);
    shopInfoDtoToUpdate.setCategory("패스트푸드");

    String token = setLoginInfo();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<ShopInfoDto> updateShopRequest = new HttpEntity<>(shopInfoDtoToUpdate, headers);
    ResponseEntity<CommonResponse<ShopInfoDto>> updateShopResult = testRestTemplate
        .exchange("/owners/shops", HttpMethod.PUT, updateShopRequest,
            new ParameterizedTypeReference<CommonResponse<ShopInfoDto>>() {
            });

    assertThat(updateShopResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    CommonResponse<ShopInfoDto> body = updateShopResult.getBody();
    assertThat(body.getStatus()).isEqualTo("success");
    ShopInfoDto updatedShopInfoDto = body.getData();
    assertThat(updatedShopInfoDto.getId()).isEqualTo(shopInfoDtoToUpdate.getId());
    assertThat(updatedShopInfoDto.getName()).isEqualTo(shopInfoDtoToUpdate.getName());
    assertThat(updatedShopInfoDto.getDescription()).isEqualTo(shopInfoDtoToUpdate.getDescription());
    assertThat(updatedShopInfoDto.getCategory()).isEqualTo(shopInfoDtoToUpdate.getCategory());
    assertThat(updatedShopInfoDto.getAddress()).isEqualTo(shopInfoDtoToUpdate.getAddress());
    assertThat(updatedShopInfoDto.getIsOpen()).isEqualTo(shopInfoDtoToUpdate.getIsOpen());

  }

  @Test
  @DisplayName("Owner의 가게 OPEN/CLOSE 상태 변경 API")
  void updateShopStatus() {
    ShopInfoDto addedShopInfoDto = getAddedShopInfoDto();
    Boolean isNowOpen = addedShopInfoDto.getIsOpen();
    ShopStatusDto shopStatusDtoToUpdate = new ShopStatusDto(addedShopInfoDto.getId(), !isNowOpen);

    String token = setLoginInfo();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<ShopStatusDto> updateShopStatusRequest = new HttpEntity<>(shopStatusDtoToUpdate,
        headers);
    ResponseEntity<CommonResponse<ShopStatusDto>> updateShopResult = testRestTemplate
        .exchange("/owners/shops/status", HttpMethod.PATCH, updateShopStatusRequest,
            new ParameterizedTypeReference<CommonResponse<ShopStatusDto>>() {
            });

    assertThat(updateShopResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    CommonResponse<ShopStatusDto> body = updateShopResult.getBody();
    assertThat(body.getStatus()).isEqualTo("success");
    ShopStatusDto updatedShopStatusDto = body.getData();
    assertThat(updatedShopStatusDto.getId()).isEqualTo(shopStatusDtoToUpdate.getId());
    assertThat(isNowOpen).isNotEqualTo(updatedShopStatusDto.getIsOpen());
  }

  private ShopInfoDto getAddedShopInfoDto() {
    ShopRegisterDto shopRegisterDto = new ShopRegisterDto();
    shopRegisterDto.setName("프랭크 버거");
    shopRegisterDto.setDescription("버거 맛있음");
    shopRegisterDto.setCategory("패스트푸드");
    shopRegisterDto.setAddress("서울시 강서구 양천로23길 9");
    shopRegisterDto.setImage("image.jpg");

    String token = setLoginInfo();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<ShopRegisterDto> addShopRequest = new HttpEntity<>(shopRegisterDto, headers);
    ResponseEntity<CommonResponse<ShopInfoDto>> addShopResult = testRestTemplate
        .exchange("/owners/shops", HttpMethod.POST, addShopRequest,
            new ParameterizedTypeReference<CommonResponse<ShopInfoDto>>() {
            });
    ShopInfoDto addedShopInfoDto = addShopResult.getBody().getData();

    return addedShopInfoDto;
  }
}