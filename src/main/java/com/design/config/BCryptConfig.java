
package com.design.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptConfig {

    @Bean
    public BCryptPasswordEncoder initBCrypt() {
        return new BCryptPasswordEncoder();
    }

}