package com.delivery_service.service;

import com.delivery_service.dto.ImageUrlDto;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUrlService {

  @Value("${ncp.storage.bucket}")
  private String bucket;
  private final S3Presigner s3Presigner;
  private final long expMinute = 30;


  public ImageUrlDto getImageUrlDto(String originalImageName) {
    String key = generateKey(originalImageName);

    AwsRequestOverrideConfiguration override = AwsRequestOverrideConfiguration.builder()
        .putRawQueryParameter("x-amz-acl", "public-read")//업로드 후 public 읽기 설정
        .build();

    PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(expMinute)) // 30분 동안 유효
        .putObjectRequest(PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .overrideConfiguration(override)
            .build())
        .build();

    PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(
        putObjectPresignRequest);
    log.debug("presignedUrl={}", presignedRequest.url());

    return new ImageUrlDto(presignedRequest.url().toString(), key);
  }

  private String generateKey(String filename) {
    UUID uuid = UUID.randomUUID();
    String ext = filename.substring(filename.lastIndexOf("."));

    return uuid.toString() + ext;
  }

}
