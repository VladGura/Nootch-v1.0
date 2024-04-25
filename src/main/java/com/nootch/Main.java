package com.nootch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.nootch.*")
@EntityScan(basePackages = "com.nootch.entities")
@EnableJpaRepositories(basePackages = "com.nootch.repositories")
class Nootch {
    private static final Logger log = LoggerFactory.getLogger(Nootch.class); //to be used in case of errors

    public static void main(String[] args) {
        SpringApplication.run(Nootch.class, args);
    }

}
