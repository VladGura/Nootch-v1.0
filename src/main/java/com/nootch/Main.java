package com.nootch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan({"com.delivery.request"})
@EntityScan("com.delivery.domain")
@EnableJpaRepositories(basePackages = "repository")
class Nootch {


    private static final Logger log = LoggerFactory.getLogger(Nootch.class);

    public static void main(String[] args) {
        SpringApplication.run(Nootch.class, args);

    }

    @GetMapping("/hello")
    public List<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return List.of("Hello", name);
    }
}
