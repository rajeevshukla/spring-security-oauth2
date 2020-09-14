package com.developervisits.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Principal;

@RestController
public class AuthController {

    @GetMapping("/securedPage")
    public Principal securedPage(Model model, Principal principal) {

        return principal;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        return "index";
    }
}
