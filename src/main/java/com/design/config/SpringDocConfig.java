package com.design.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SpringDocConfig {

    @Value("${server.client.url}")
    private String clientUrl;

    @Bean
    public OpenAPI initSpringDoc(ServletContext servletContext) {
        final String url = String.format("%s%s", clientUrl, servletContext.getContextPath());
        Server local = new Server();
        local.setUrl(url);
        return new OpenAPI().servers(List.of(local));
    }

}