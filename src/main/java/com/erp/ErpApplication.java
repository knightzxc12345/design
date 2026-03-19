package com.erp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ErpApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private InitService initService;

    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ErpApplication.class);
    }

    @Override
    public void run(String... args) {
        initService.init();
    }

}
