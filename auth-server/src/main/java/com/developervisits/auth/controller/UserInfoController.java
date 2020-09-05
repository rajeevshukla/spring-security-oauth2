package com.developervisits.auth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;

@RestController
public class UserInfoController {
    @GetMapping("/userinfo")
    public HashMap<String, Object> userInfo(Principal principal) {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("username", principal.getName());
        hashMap.put("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        System.out.println("User Info is called");

        return hashMap;
    }

    @GetMapping("/formLoginSuccess")
    public String loginSuccess(Principal principal) {
        System.out.println("User is able to login" + principal.getName());

        return principal.getName();
    }

    @GetMapping("/")
    public String home(Principal principal) {
        System.out.println("User is able to login" + principal.getName());

        return principal.getName();
    }

}
