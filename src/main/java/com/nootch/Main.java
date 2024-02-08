package com.nootch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@SpringBootApplication
@RestController
class Nootch {

    public static void main(String[] args) {
        SpringApplication.run(Nootch.class, args);
    }

    @GetMapping("/hello")
    public List<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return List.of("Hello", name);
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView register(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
        //add to database
        return new RedirectView("/");
    }

    @RequestMapping(value = "/home",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        System.out.println(username + " " + password.hashCode());
        return "Boohoohooo";
    }

    @GetMapping("/home")
    public RedirectView homeError() {
        return new RedirectView("/");
    }
}
