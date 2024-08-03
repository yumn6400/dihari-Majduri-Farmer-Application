package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.dto.ChangeOwnerPin;
import com.dihari.majduri.DihariMajduri.mobile.dto.LoginRequest;
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
        ResponseWrapper responseWrapper = new ResponseWrapper(true, farmers);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFarmerById(@PathVariable int id) {
        Optional<Farmer> farmer = farmerService.getFarmerById(id);
        if (!farmer.isPresent()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Farmer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper responseWrapper = new ResponseWrapper(true, farmer.get());
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping
    public ResponseEntity<?> addFarmer(@Validated @RequestBody Farmer farmer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        farmerService.addFarmer(farmer);
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Farmer added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(responseWrapper));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFarmer(@PathVariable int id, @Validated @RequestBody Farmer updatedFarmer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        if (!farmerService.updateFarmer(id, updatedFarmer)) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Farmer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Farmer updated successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmer(@PathVariable int id) {
        if (!farmerService.deleteFarmer(id)) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Farmer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Farmer deleted successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping("/validateFarmerPin")
    public ResponseEntity<?> validateFarmerPin(@Validated @RequestBody LoginRequest loginRequest) {
        ResponseWrapper responseWrapper = farmerService.validateFarmerPin(loginRequest);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PutMapping("/changePin")
    public ResponseEntity<?> changeFarmerPin(@Validated @RequestBody ChangeOwnerPin changeOwnerPin) {
        ResponseWrapper responseWrapper = farmerService.changeFarmerPin(changeOwnerPin);
        if (!responseWrapper.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }
}
