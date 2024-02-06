package com.nootch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
class Nootch {

    public static void main(String[] args) {
        SpringApplication.run(Nootch.class, args);
    }

    @GetMapping("/hello")
    public List<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return List.of("Hello", name);
    }
}
