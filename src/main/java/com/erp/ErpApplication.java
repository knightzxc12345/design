package com.erp;

import com.erp.service.InitRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class ErpApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private final InitRoleService initRoleService;

    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ErpApplication.class);
    }

    @Override
    public void run(String... args) {
        initRoleService.init();
    }

}
