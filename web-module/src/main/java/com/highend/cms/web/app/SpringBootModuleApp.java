package com.highend.cms.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = "com.highend.cms.*",
        exclude = UserDetailsServiceAutoConfiguration.class
)
@EntityScan("com.highend.cms.repository.model")
public class SpringBootModuleApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootModuleApp.class, args);
    }
}
