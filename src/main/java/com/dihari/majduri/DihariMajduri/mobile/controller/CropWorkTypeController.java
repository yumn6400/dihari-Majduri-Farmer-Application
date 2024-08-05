package com.dihari.majduri.DihariMajduri.mobile.controller;


import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.model.CropWorkType;
import com.dihari.majduri.DihariMajduri.mobile.service.CropWorkTypeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/crop-work-types")
public class CropWorkTypeController {

    @Autowired
    private CropWorkTypeService cropWorkTypeService;

    @GetMapping
    public ResponseEntity<?> getAllCropWorkTypes() {
        List<CropWorkType> cropWorkTypes = cropWorkTypeService.findAll();
        ResponseWrapper<List<CropWorkType>> responseWrapper = new ResponseWrapper<>(true,  cropWorkTypes);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

}
