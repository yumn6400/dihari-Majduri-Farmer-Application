package com.dihari.majduri.DihariMajduri.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {

    @GetMapping("/")
    public String hello(){
        return "Welcome to Dihari-Majduri Application";
    }

    @GetMapping("/testing/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }


    @GetMapping("/testing/session-id")
    public String getSessionId(HttpServletRequest request){
        return "Session Id : "+request.getSession().getId();
    }

    @PostMapping("/testing/security")
    public String testing(){

        return "Testing complete ";
    }
}
