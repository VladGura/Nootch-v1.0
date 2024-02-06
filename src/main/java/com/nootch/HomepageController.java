package com.nootch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class HomepageController {

    @GetMapping("/home")
    public RedirectView homeError() {
        return new RedirectView("/");
    }

    //TODO: add more features:)
}
