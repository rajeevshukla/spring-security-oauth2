package com.developervisits.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Principal;

@Controller
public class AuthController {

    @GetMapping("/userInfo")
     public String userInfo(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    @PostConstruct
    public void init() {
        System.out.println("working");
    }
}
