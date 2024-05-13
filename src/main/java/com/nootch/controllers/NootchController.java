package com.nootch.controllers;


import com.nootch.entities.NootchUser;
import com.nootch.repositories.UserRepository;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;


@Controller
public class NootchController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ServletContext context;


    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password, Model model) {

        NootchUser user = new NootchUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password.hashCode());

        if(userRepository.findAll().stream().anyMatch(nootchUser -> nootchUser.getUsername().equals(username))) {
            model.addAttribute("alert", "The username you're trying is already taken!");
            model.addAttribute("dataSubmitted", true);
            return "create_account"; // Return to the form with the alert
        }

        if(userRepository.findAll().stream().anyMatch(nootchUser -> nootchUser.getEmail().equals(email))) {
            model.addAttribute("alert", "The email you're trying already has an account!");
            model.addAttribute("dataSubmitted", true);
            return "create_account"; // Return to the form with the alert
        }

        userRepository.saveAndFlush(user);
        model.addAttribute("alert", "Successfully created account!");
        model.addAttribute("dataSubmitted", true);
        return "index"; // Redirect to the index or a successful registration page
    }

    @PostMapping(value = "/home",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, Model model) {
        ArrayList<Long> matches = new ArrayList<>();
        userRepository.findAll().stream()
                .filter(nootchUser -> nootchUser.getUsername().equals(username) && nootchUser.getPassword() == password.hashCode())
                .forEach(user -> matches.add(user.getId()));
        if(matches.isEmpty()) {
            model.addAttribute("alert", "Incorrect username or password!");
            model.addAttribute("dataSubmitted", true);
        }
        else {
            model.addAttribute("profile", username);
            String absolutePath = context.getRealPath("/");
            System.out.println(absolutePath);
            return "home";
        }
        return "index";
    }

    @GetMapping("/home")
    public RedirectView homeError() {
        return new RedirectView("/");
    }

    @GetMapping("/forgot_password")
    public String forgot_password(){
        return "forgot_password";
    }

    @GetMapping("/create_account")
    public String create_account() {
        return "create_account";
    }
}
