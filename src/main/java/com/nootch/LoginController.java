package com.nootch;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class LoginController {
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
        //check login credentials from database data
        return "Boohoo n-word";
    }
}
