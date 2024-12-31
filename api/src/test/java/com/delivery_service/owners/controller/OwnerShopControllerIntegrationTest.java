package com.delivery_service.owners.controller;

import com.delivery_service.owners.dto.ShopInfoDto;
import com.delivery_service.owners.dto.ShopRegisterDto;
import com.delivery_service.owners.dto.ShopStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class OwnerShopControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private boolean isSessionSet = false;
    private final String SESSION_KEY = "JSESSIONID";
    private String sessionId = null;

    @BeforeEach
    @DisplayName("임시 ownerId 세션 작업")
    void setTestSession() {
        if(!isSessionSet){
            String sessId = testRestTemplate.postForObject("/owners/test-session", null, String.class);
            log.info("sessId={}", sessId);
            sessionId = sessId;
            isSessionSet = true;
        }
    }

    @Test
    @DisplayName("Owner의 가게 등록,조회,수정 테스트")
    void test() {
        //가게 등록
        ShopRegisterDto shopRegisterDto = new ShopRegisterDto();
        shopRegisterDto.setName("프랭크 버거");
        shopRegisterDto.setDescription("버거 맛있음");
        shopRegisterDto.setCategory("패스트푸드");
        shopRegisterDto.setAddress("서울시 강서구 양천로23길 9");

        HttpEntity<ShopRegisterDto> addShopRequest = setRequestWithSessionCookie(shopRegisterDto);
        ResponseEntity<String> addShopResult = testRestTemplate.exchange("/owners/shops", HttpMethod.POST, addShopRequest, String.class);

        assertThat(addShopResult.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(addShopResult.getBody()).isEqualTo("CREATED");


        //가게 조회
        HttpEntity<?> getShopRequest = setRequestWithSessionCookie(null);
        ResponseEntity<ShopInfoDto> getShopResult = testRestTemplate.exchange("/owners/shops", HttpMethod.GET, getShopRequest, ShopInfoDto.class);

        assertThat(getShopResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        ShopInfoDto shopInfo = getShopResult.getBody();
        assertThat(shopInfo.getId()).isNotNull();
        assertThat(shopInfo.getName()).isEqualTo(shopRegisterDto.getName());
        assertThat(shopInfo.getDescription()).isEqualTo(shopRegisterDto.getDescription());
        assertThat(shopInfo.getCategory()).isEqualTo(shopRegisterDto.getCategory());
        assertThat(shopInfo.getAddress()).isEqualTo(shopRegisterDto.getAddress());

        //가게 수정
        ShopInfoDto shopInfoForUpdate = new ShopInfoDto();
        shopInfoForUpdate.setId(shopInfo.getId());
        shopInfoForUpdate.setName("사모님짬뽕");
        shopInfoForUpdate.setDescription("짬뽕 맛있음");
        shopInfoForUpdate.setCategory("중식");
        shopInfoForUpdate.setAddress("서울시 강서구 양천로7길 14");

        HttpEntity<?> updateShopRequest = setRequestWithSessionCookie(shopInfoForUpdate);
        ResponseEntity<String> updateShopResult = testRestTemplate.exchange("/owners/shops", HttpMethod.PUT, updateShopRequest, String.class);
        assertThat(updateShopResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateShopResult.getBody()).isEqualTo("UPDATED");

        //가게 OPEN/CLOSE 상태 변경
        ShopStatusDto shopStatusDto = new ShopStatusDto(!shopInfo.getIsOpen());
        HttpEntity<?> updateShopStatusRequest = setRequestWithSessionCookie(shopStatusDto);
        ResponseEntity<String> updateShopStatusResult = testRestTemplate.exchange("/owners/shops/status", HttpMethod.PATCH, updateShopStatusRequest, String.class);
        assertThat(updateShopStatusResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateShopStatusResult.getBody()).isEqualTo("UPDATED");

    }




    private <T> HttpEntity<T> setRequestWithSessionCookie(T requestData) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",SESSION_KEY+"="+sessionId);

        if(requestData == null){
            return new HttpEntity<>(headers);
        }

        HttpEntity<T> request = new HttpEntity<>(requestData, headers);
        return request;
    }

}