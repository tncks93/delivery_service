package com.delivery_service.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlProvider {

  //  private static final String publicUrl = "https://delivery-service.kr.object.ncloudstorage.com/";
  private static String publicUrl;

  @Value("${ncp.storage.public-url}")
  public void setPublicUrl(String publicUrl) {
    this.publicUrl = publicUrl;
  }

  public static String getPublicUrl(String fileName) {
    return publicUrl + fileName;
  }
}
