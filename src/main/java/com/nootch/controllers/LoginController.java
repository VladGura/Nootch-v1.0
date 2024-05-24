package com.nootch.controllers;


import com.nootch.entities.NootchUser;
import com.nootch.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/home",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                        Model model, HttpServletRequest request, HttpSession session) {
        return login(username, password, false, model, request, session);
    }

    @GetMapping("/home")
    public String homeError(Model model, HttpServletRequest request, HttpSession session) {
        NootchUser user = userRepository.getUserByHttpSessionId(session.getId());
        if(user != null)
            return login(user.getUsername(), Long.toString(user.getPassword()), true, model, request, session);
        return "index";
    }


    private String login(String username, String password, boolean hashed, Model model, HttpServletRequest request, HttpSession session) {

        session.invalidate();
        HttpSession newSession = request.getSession();
        ArrayList<Long> matches = new ArrayList<>();
        if(hashed)
            userRepository.findAll().stream()
                    .filter(nootchUser -> nootchUser.getUsername().equalsIgnoreCase(username) && nootchUser.getPassword() == Long.parseLong(password))
                    .forEach(user -> matches.add(user.getId()));
        else
            userRepository.findAll().stream()
                .filter(nootchUser -> nootchUser.getUsername().equalsIgnoreCase(username) && nootchUser.getPassword() == password.hashCode())
                .forEach(user -> matches.add(user.getId()));
        if(matches.isEmpty()) {
            model.addAttribute("alert", "Incorrect username or password!");
            model.addAttribute("dataSubmitted", true);
        }
        else {
            NootchUser user = userRepository.getReferenceById(matches.get(0));
            user.setHttpSessionId(newSession.getId());
            userRepository.saveAndFlush(user);

            model.addAttribute("profile", user.getUsername());
            return "home";
        }
        return "index";
    }
}
