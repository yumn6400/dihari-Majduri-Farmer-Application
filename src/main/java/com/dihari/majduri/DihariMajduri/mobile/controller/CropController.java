package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.model.Crop;
import com.dihari.majduri.DihariMajduri.mobile.service.CropService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/crops")
public class CropController {

    @Autowired
    private CropService cropService;
    @GetMapping
    public ResponseEntity<?> getAllCrops() {
        List<Crop> crops = cropService.findAll();
        ResponseWrapper responseWrapper = new ResponseWrapper(true,  crops);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

}
