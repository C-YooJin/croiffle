package com.cfww.croiffle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CroiffleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CroiffleApplication.class, args);
    }

}
