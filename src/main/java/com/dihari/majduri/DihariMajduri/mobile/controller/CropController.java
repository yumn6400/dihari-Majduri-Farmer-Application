package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.model.Crop;
import com.dihari.majduri.DihariMajduri.mobile.service.CropService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mobile/crops")
public class CropController {

    @Autowired
    private CropService cropService;
    @GetMapping
    public ResponseEntity<?> getAllCrops() {
        List<Crop> crops = cropService.findAll();
        ResponseWrapper<List<Crop>> responseWrapper = new ResponseWrapper<>(true,  crops);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

}
