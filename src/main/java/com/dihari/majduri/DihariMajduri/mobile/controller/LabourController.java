package com.dihari.majduri.DihariMajduri.mobile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.common.StatusCode;
import com.dihari.majduri.DihariMajduri.mobile.dto.LabourPojo;
import com.dihari.majduri.DihariMajduri.mobile.model.Labour;
import com.dihari.majduri.DihariMajduri.mobile.service.LabourService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/labours")
public class LabourController {

    @Autowired
    private LabourService labourService;

    @GetMapping
    public ResponseEntity<?> getAllLabours() {
        List<Labour> labours = labourService.findAllLabours();
        List<LabourPojo> list = new ArrayList<>();
        for (Labour labour : labours) {
            LabourPojo labourResponse = new LabourPojo(labour.getId(), labour.getName(), labour.getMobileNumber());
            list.add(labourResponse);
        }
        ResponseWrapper responseWrapper = new ResponseWrapper(true, list);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLabourById(@PathVariable int id) {
        Optional<Labour> labour = labourService.getLabourById(id);
        if (!labour.isPresent()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, StatusCode.LABOUR_NOT_EXISTS.getCode(), "Labour does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        LabourPojo labourResponse = new LabourPojo(labour.get().getId(), labour.get().getName(), labour.get().getMobileNumber());
        ResponseWrapper responseWrapper = new ResponseWrapper(true, labourResponse);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping
    public ResponseEntity<?> addLabour(@Validated @RequestBody Labour labour, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        labourService.addLabour(labour);
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Labour added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(responseWrapper));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLabour(@PathVariable int id, @Validated @RequestBody Labour updatedLabour, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        if (!labourService.updateLabour(id, updatedLabour)) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, StatusCode.LABOUR_NOT_EXISTS.getCode(), "Labour does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Labour updated successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLabour(@PathVariable int id) {
        if (!labourService.deleteLabour(id)) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, StatusCode.LABOUR_NOT_EXISTS.getCode(), "Labour does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Labour deleted successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }
}
