package com.delivery_service.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncConfig {


  @Bean
  public ExecutorService executorService() {
    return Executors.newCachedThreadPool();
  }

}
