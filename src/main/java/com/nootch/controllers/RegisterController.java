package com.nootch.controllers;

import com.nootch.entities.NootchUser;
import com.nootch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/create_account")
    public String create_account() {
        return "create_account";
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password, Model model) {

        NootchUser user = new NootchUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password.hashCode());

        if(userRepository.findAll().stream().anyMatch(nootchUser -> nootchUser.getUsername().equalsIgnoreCase(username))) {
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
}
