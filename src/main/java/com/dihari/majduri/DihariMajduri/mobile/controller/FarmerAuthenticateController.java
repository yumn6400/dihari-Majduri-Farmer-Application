package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.pojo.LoginRequestPojo;
import com.dihari.majduri.DihariMajduri.mobile.service.FarmerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class FarmerAuthenticateController {

    @Autowired
    FarmerService farmerService;


    @PostMapping("/authenticate")
    public ResponseEntity<?> validateFarmerPin(@Validated @RequestBody LoginRequestPojo loginRequestPojo) {
        ResponseWrapper<String> responseWrapper = farmerService.validateFarmerPin(loginRequestPojo);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }
}
