package com.developervisits.auth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;

@RestController
public class UserInfoController {
    @GetMapping("/user/me")
    public Principal userInfo(Principal principal) {
        System.out.println("/user/me called to obtain user information");
        return principal;
    }

}
