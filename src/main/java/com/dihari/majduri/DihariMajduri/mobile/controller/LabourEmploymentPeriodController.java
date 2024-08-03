package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.dto.DihariRequest;
import com.dihari.majduri.DihariMajduri.mobile.dto.LabourEmploymentPeriodPojo;
import com.dihari.majduri.DihariMajduri.mobile.model.LabourEmploymentPeriod;
import com.dihari.majduri.DihariMajduri.mobile.service.LabourEmploymentPeriodService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labour-employment-periods")
public class LabourEmploymentPeriodController {

    @Autowired
    private LabourEmploymentPeriodService labourEmploymentPeriodService;

    @GetMapping
    public ResponseEntity<String> getAllLabourEmploymentPeriods() {
        List<LabourEmploymentPeriod> labourEmploymentPeriods = labourEmploymentPeriodService.findAll();
        ResponseWrapper responseWrapper = new ResponseWrapper(true, labourEmploymentPeriods);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getLabourEmploymentPeriodById(@PathVariable int id) {
        Optional<LabourEmploymentPeriod> labourEmploymentPeriod = labourEmploymentPeriodService.findById(id);
        if (labourEmploymentPeriod.isPresent()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(true, labourEmploymentPeriod.get());
            return ResponseEntity.ok(new Gson().toJson(responseWrapper));
        } else {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Labour Employment Period not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
    }

    @PostMapping
    public ResponseEntity<String> addLabourEmploymentPeriod(
            @Validated @RequestBody LabourEmploymentPeriod labourEmploymentPeriod, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        labourEmploymentPeriodService.save(labourEmploymentPeriod);
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Labour Employment Period added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/farmerId/{id}")
    public ResponseEntity<String> getLabourEmploymentPeriodsByFarmerId(@PathVariable int id) {
        List<LabourEmploymentPeriodPojo> responseList = labourEmploymentPeriodService.getLabourEmploymentPeriodsByFarmerId(id);
        ResponseWrapper responseWrapper = new ResponseWrapper(true, responseList);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping("/{ownerId}")
    public ResponseEntity<String> addLabourEmploymentPeriodByOwnerId(
            @PathVariable int ownerId,
            @Validated @RequestBody DihariRequest dihariRequest, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }

        String response = labourEmploymentPeriodService.addLabourEmploymentPeriodByFarmerId(ownerId, dihariRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLabourEmploymentPeriod(
            @PathVariable int id,
            @Validated @RequestBody LabourEmploymentPeriod updatedLabourEmploymentPeriod,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        boolean updated = labourEmploymentPeriodService.update(id, updatedLabourEmploymentPeriod);
        if (updated) {
            ResponseWrapper responseWrapper = new ResponseWrapper(true, "Labour Employment Period updated successfully");
            return ResponseEntity.ok(new Gson().toJson(responseWrapper));
        } else {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Labour Employment Period not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLabourEmploymentPeriod(@PathVariable int id) {
        boolean deleted = labourEmploymentPeriodService.delete(id);
        if (deleted) {
            ResponseWrapper responseWrapper = new ResponseWrapper(true, "Labour Employment Period deleted successfully");
            return ResponseEntity.noContent().build(); // No content but indicate success
        } else {
            ResponseWrapper responseWrapper = new ResponseWrapper(false, "Labour Employment Period not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
    }
}
