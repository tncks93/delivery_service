package com.delivery_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageUrlDto {

  private String urlForUpload;
  private String urlForDownload;

}
