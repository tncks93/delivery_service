package com.delivery_service.owners.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.owners.dto.ShopInfoDto;
import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.dto.ShopStatusDto;
import com.delivery_service.owners.repository.OwnerShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class OwnerShopControllerIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private OwnerShopRepository ownerShopRepository;

  private final String TYPE_OWNER = "owner";
  private final String ownerId = "1";

  @AfterEach
  void afterEach() {
    ownerShopRepository.clear();
  }

  @Test
  @DisplayName("Owner의 가게 등록 API")
  void addShop() {

    ShopRegisterDto shopRegisterDto = new ShopRegisterDto();
    shopRegisterDto.setName("프랭크 버거");
    shopRegisterDto.setDescription("버거 맛있음");
    shopRegisterDto.setCategory("패스트푸드");
    shopRegisterDto.setAddress("서울시 강서구 양천로23길 9");

    HttpEntity<ShopRegisterDto> addShopRequest = new HttpEntity<>(shopRegisterDto);
    ResponseEntity<CommonResponse<ShopInfoDto>> addShopResult = testRestTemplate.withBasicAuth(
            TYPE_OWNER, ownerId)
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

    HttpEntity<Void> getShopRequest = new HttpEntity(null, null);
    ResponseEntity<CommonResponse<ShopInfoDto>> getShopResult = testRestTemplate.withBasicAuth(
            TYPE_OWNER, ownerId)
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

    HttpEntity<ShopInfoDto> updateShopRequest = new HttpEntity<>(shopInfoDtoToUpdate);
    ResponseEntity<CommonResponse<ShopInfoDto>> updateShopResult = testRestTemplate.withBasicAuth(
            TYPE_OWNER, ownerId)
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

    HttpEntity<ShopStatusDto> updateShopStatusRequest = new HttpEntity<>(shopStatusDtoToUpdate);
    ResponseEntity<CommonResponse<ShopStatusDto>> updateShopResult = testRestTemplate.withBasicAuth(
            TYPE_OWNER, ownerId)
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

    HttpEntity<ShopRegisterDto> addShopRequest = new HttpEntity<>(shopRegisterDto);
    ResponseEntity<CommonResponse<ShopInfoDto>> addShopResult = testRestTemplate.withBasicAuth(
            TYPE_OWNER, ownerId)
        .exchange("/owners/shops", HttpMethod.POST, addShopRequest,
            new ParameterizedTypeReference<CommonResponse<ShopInfoDto>>() {
            });
    ShopInfoDto addedShopInfoDto = addShopResult.getBody().getData();

    return addedShopInfoDto;
  }
}