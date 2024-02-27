package com.nootch.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ErrorHandlerController implements ErrorController {

    @RequestMapping("/error")
    @ExceptionHandler(NoHandlerFoundException.class)
    public RedirectView error() {
        return new RedirectView("/error.html");
    }

}
