package com.nootch.controllers;


import com.nootch.entities.NootchUser;
import com.nootch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Controller
public class NootchController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView register(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {

        NootchUser user = new NootchUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password.hashCode());

        if(userRepository.findAll().stream().anyMatch(nootchUser -> nootchUser.getUsername().equals(username)))
            return new RedirectView("/error.html"); //somehow tell the user the username is taken

        if(userRepository.findAll().stream().anyMatch(nootchUser -> nootchUser.getEmail().equals(email)))
            return new RedirectView("/error.html"); //somehow tell the user the email is taken

        userRepository.saveAndFlush(user);
        return new RedirectView("/");
    }

    @PostMapping(value = "/home",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        ArrayList<Long> matches = new ArrayList<>();
        userRepository.findAll().stream()
                .filter(nootchUser -> nootchUser.getUsername().equals(username) && nootchUser.getPassword() == password.hashCode())
                .forEach(user -> matches.add(user.getId()));
        if(matches.isEmpty())
            return new RedirectView("/error.html");
        else
            return new RedirectView("/home.html");
    }

    @GetMapping("/home")
    public RedirectView homeError() {
        return new RedirectView("/");
    }
}
