package com.delivery_service.common.config;

import com.delivery_service.owners.resolver.OwnerArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final OwnerArgumentResolver ownerArgumentResolver;

    public WebConfig(OwnerArgumentResolver ownerArgumentResolver) {
        this.ownerArgumentResolver = ownerArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(ownerArgumentResolver);
    }

}
