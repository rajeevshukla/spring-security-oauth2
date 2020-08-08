package com.developervisits.auth.controller;

import com.nimbusds.jose.proc.SecurityContext;
import com.sun.security.auth.PrincipalComparator;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;

@RestController
public class UserInfoController {
    @GetMapping("/userinfo")
    public HashMap<String, Object> userInfo(Principal principal) {
        HashMap<String, Object> userInfo = new HashMap<>();


        userInfo.put("userName", principal.getName());
        userInfo.put("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities());


        return userInfo;
    }
}
