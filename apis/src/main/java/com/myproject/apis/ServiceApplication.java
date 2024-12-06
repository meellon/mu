package com.myproject.apis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(
        basePackages = { "com.myproject" }
)
public class ServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServiceApplication.class);
    }

    public static void main(String[] args) {
        log.info("Starting ServiceApplication...");
        new SpringApplicationBuilder(ServiceApplication.class)
                .build()
                .run(args);
        log.info("ServiceApplication started successfully.");
    }
}
