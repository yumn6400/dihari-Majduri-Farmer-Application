package com.dihari.majduri.DihariMajduri.web.controller;

import com.dihari.majduri.DihariMajduri.web.model.User;
import com.dihari.majduri.DihariMajduri.security.service.JwtService;
import com.dihari.majduri.DihariMajduri.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/register")
    public User register(@RequestBody User user)
    {
        System.out.println("*********Register method got called********");
    return userService.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        else return "Invalid username or password";

    }
}
