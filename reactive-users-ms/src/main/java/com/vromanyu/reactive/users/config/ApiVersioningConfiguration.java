package com.vromanyu.reactive.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ApiVersionConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class ApiVersioningConfiguration implements WebFluxConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.usePathSegment(1)
                .addSupportedVersions("1.0")
                .setDefaultVersion("1.0");

    }
}
