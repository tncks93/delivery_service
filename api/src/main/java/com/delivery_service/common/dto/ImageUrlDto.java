package com.delivery_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ImageUrlDto {

  private String urlForUpload;
  private String imageFileName;

}
