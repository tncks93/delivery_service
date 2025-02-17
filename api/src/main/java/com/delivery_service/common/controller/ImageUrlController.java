package com.delivery_service.common.controller;

import com.delivery_service.common.dto.ImageUploadReqDto;
import com.delivery_service.common.dto.ImageUrlDto;
import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.common.service.ImageUrlService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ImageUrlController {

  private final ImageUrlService imageUrlService;
  private final ExecutorService executorService;

  @PostMapping("/request/image-url")
  public CompletableFuture<ResponseEntity<CommonResponse<ImageUrlDto>>> getShopImageUrl(
      @RequestBody ImageUploadReqDto imageUploadReqDto) {

    return CompletableFuture.supplyAsync(
            () -> imageUrlService.getImageUrlDto(imageUploadReqDto.getOriginalImageName()),
            executorService)
        .thenApply(CommonResponse::success)
        .thenApply(res -> new ResponseEntity<>(res, HttpStatus.OK));
  }

}
