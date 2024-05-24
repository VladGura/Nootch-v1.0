package com.nootch.controllers;

import com.nootch.entities.NootchUser;
import com.nootch.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomepageController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/own_profile")
    public String own_profile(Model model, HttpSession session) {
        NootchUser user = userRepository.getUserByHttpSessionId(session.getId());

        model.addAttribute("username", user.getUsername());
        return "own_profile";
    }
}
