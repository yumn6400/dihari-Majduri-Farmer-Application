package com.dihari.majduri.DihariMajduri.web.controller;

import com.dihari.majduri.DihariMajduri.web.service.TestingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/web")
@RestController
public class TestingController {

    @Autowired
    TestingService testingService;
    @GetMapping("/")
    public String hello(){
        return testingService.hello();
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
