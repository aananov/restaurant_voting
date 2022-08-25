package ru.javaops.topjava2.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class RootController {

    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    @GetMapping("/")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
}
