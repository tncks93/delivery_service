package com.delivery_service.common.config;

import java.net.URI;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@ConfigurationProperties(prefix = "ncp.storage")
@Getter
@Setter
public class NCPStorageConfig {

  private String endpoint;
  private String accessKey;
  private String secretKey;
  private String region;
  private String bucket;

  @Bean
  public S3Presigner s3Presigner() {
    AwsBasicCredentials credentials = AwsBasicCredentials.builder()
        .accessKeyId(accessKey)
        .secretAccessKey(secretKey)
        .build();

    return S3Presigner.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .endpointOverride(URI.create(endpoint))
        .build();
  }
}
