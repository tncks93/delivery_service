package com.delivery_service.owners.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery_service.common.dto.ImageUploadReqDto;
import com.delivery_service.common.dto.ImageUrlDto;
import com.delivery_service.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
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
public class ImageUrlControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void getImageUrl() {
    String originalFileName = "image.jpg";
    ImageUploadReqDto imageUploadReqDto = new ImageUploadReqDto(originalFileName);

    HttpEntity<ImageUploadReqDto> request = new HttpEntity<>(imageUploadReqDto);
    ResponseEntity<CommonResponse<ImageUrlDto>> response = testRestTemplate.exchange(
        "/request/image-url",
        HttpMethod.POST, request, new ParameterizedTypeReference<CommonResponse<ImageUrlDto>>() {
        });

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getData()).isNotNull();
    ImageUrlDto imageUrlDto = response.getBody().getData();
    assertThat(imageUrlDto.getUrlForUpload()).isNotNull();
    assertThat(imageUrlDto.getImageFileName()).isNotNull();

    log.debug(imageUrlDto.toString());

  }

}
