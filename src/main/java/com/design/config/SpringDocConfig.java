package com.design.config;

import io.swagger.v3.oas.models.OpenAPI;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringDocConfig {

    @Bean
    public OpenAPI initSpringDoc(ServletContext servletContext) {
        return new OpenAPI();
    }

}