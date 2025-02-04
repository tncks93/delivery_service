package com.delivery_service.common.controller;

import com.delivery_service.common.dto.ImageUploadReqDto;
import com.delivery_service.common.dto.ImageUrlDto;
import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.common.service.ImageUrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ImageUrlController {

  private final ImageUrlService imageUrlService;

  @PostMapping("/request/image-url")
  public ResponseEntity<CommonResponse<ImageUrlDto>> getShopImageUrl(
      @RequestBody ImageUploadReqDto imageUploadReqDto) {
    ImageUrlDto imageUrlDto = imageUrlService.getImageUrlDto(
        imageUploadReqDto.getOriginalImageName());

    CommonResponse<ImageUrlDto> response = CommonResponse.success(imageUrlDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
