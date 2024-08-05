package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.common.ErrorCode;
import com.dihari.majduri.DihariMajduri.mobile.pojo.ChangeFarmerPinPojo;
import com.dihari.majduri.DihariMajduri.mobile.pojo.FarmerPojo;
import com.dihari.majduri.DihariMajduri.mobile.pojo.LoginRequestPojo;
import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import com.dihari.majduri.DihariMajduri.mobile.service.FarmerService;
import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/farmers")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @GetMapping
    public ResponseEntity<?> getAllFarmers() {
        List<Farmer> farmers = farmerService.findAllFarmers();
        List<FarmerPojo> farmersPojo = new ArrayList<>();
        for (Farmer farmer:farmers) {
            farmersPojo.add(new FarmerPojo(farmer.getId(),farmer.getName(),farmer.getMobileNumber()));
        }
        ResponseWrapper<List<FarmerPojo>> responseWrapper = new ResponseWrapper<>(true, farmersPojo);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFarmerById(@PathVariable int id) {
        Optional<Farmer> farmerOptional = farmerService.getFarmerById(id);
        if (farmerOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"", ErrorCode.ID_NOT_EXISTS,"Farmer Id not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        Farmer farmer=farmerOptional.get();
        FarmerPojo farmerPojo=new FarmerPojo(farmer.getId(),farmer.getName(),farmer.getMobileNumber());
        ResponseWrapper<FarmerPojo> responseWrapper = new ResponseWrapper<>(true, farmerPojo);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping
    public ResponseEntity<?> addFarmer(@Validated @RequestBody FarmerPojo farmerPojo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"", ErrorCode.VALIDATION_ERROR, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        Farmer farmer=new Farmer(farmerPojo.getName(),farmerPojo.getMobileNumber(),farmerPojo.getPin());
        farmerService.addFarmer(farmer);
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Farmer added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(responseWrapper));
    }

    @PutMapping
    public ResponseEntity<?> updateFarmer(@Validated @RequestBody FarmerPojo updatedFarmer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.VALIDATION_ERROR, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        Farmer farmer=new Farmer(updatedFarmer.getId(),updatedFarmer.getName(),updatedFarmer.getMobileNumber(),updatedFarmer.getPin());
        if (!farmerService.updateFarmer(farmer)) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"", ErrorCode.ID_NOT_EXISTS,"Farmer Id not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Farmer updated successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmer(@PathVariable int id) {
        if (!farmerService.deleteFarmer(id)) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.ID_NOT_EXISTS ,"Farmer Id not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Farmer deleted successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping("/validateFarmerPin")
    public ResponseEntity<?> validateFarmerPin(@Validated @RequestBody LoginRequestPojo loginRequestPojo) {
        ResponseWrapper<String> responseWrapper = farmerService.validateFarmerPin(loginRequestPojo);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
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
