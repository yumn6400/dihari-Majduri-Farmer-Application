package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.common.ErrorCode;
import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import com.dihari.majduri.DihariMajduri.mobile.pojo.ChangeFarmerPinPojo;
import com.dihari.majduri.DihariMajduri.mobile.pojo.FarmerPojo;
import com.dihari.majduri.DihariMajduri.mobile.pojo.LoginRequestPojo;
import com.dihari.majduri.DihariMajduri.mobile.service.FarmerService;
import com.dihari.majduri.DihariMajduri.security.service.JwtService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/mobile")
public class FarmerAuthenticationController {

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFarmer(@Validated @RequestBody FarmerPojo farmerPojo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"", ErrorCode.VALIDATION_ERROR, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        Farmer farmer=new Farmer(farmerPojo.getFirstName(),farmerPojo.getLastName(),farmerPojo.getMobileNumber(),farmerPojo.getPin());
        int id=farmerService.addFarmer(farmer);

        Map<String, Integer> responseData = new HashMap<>();
        responseData.put("id", id);
        ResponseWrapper<Map<String,Integer>> responseWrapper = new ResponseWrapper<>(true, responseData);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(responseWrapper));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginFarmer(@Validated @RequestBody LoginRequestPojo loginRequestPojo) {
        Map<String, String> responseData = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestPojo.getMobileNumber(), loginRequestPojo.getPin()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(loginRequestPojo.getMobileNumber());
                responseData.put("token", token);
                ResponseWrapper<Map<String, String>> responseWrapper = new ResponseWrapper<>(true, responseData);
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(responseWrapper));
            } else {
                responseData.put("message", "Invalid mobileNumber or pin");
                ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.NOT_AUTHENTICATE,"Invalid mobileNumber or pin");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Gson().toJson(responseWrapper));
            }
        } catch (AuthenticationException e) {
            responseData.put("message", "Authentication failed: " + e.getMessage());
            ResponseWrapper<Map<String, String>> responseWrapper = new ResponseWrapper<>(false, responseData);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Gson().toJson(responseWrapper));
        }
    }

    @PutMapping("/changePin")
    public ResponseEntity<?> changeFarmerPin(@Validated @RequestBody ChangeFarmerPinPojo changeFarmerPinPojo) {
        ResponseWrapper<String> responseWrapper = farmerService.changeFarmerPin(changeFarmerPinPojo);
        if (!responseWrapper.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }
}
