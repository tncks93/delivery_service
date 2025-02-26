package com.delivery_service.config;

import com.delivery_service.common.resolver.LoginUserInfoArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final LoginUserInfoArgumentResolver loginUserInfoArgumentResolver;

  public WebConfig(LoginUserInfoArgumentResolver loginUserInfoArgumentResolver) {
    this.loginUserInfoArgumentResolver = loginUserInfoArgumentResolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginUserInfoArgumentResolver);
  }

}
