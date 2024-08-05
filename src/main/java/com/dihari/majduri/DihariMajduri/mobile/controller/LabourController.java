package com.dihari.majduri.DihariMajduri.mobile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.dihari.majduri.DihariMajduri.mobile.common.ErrorCode;
import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.pojo.LabourPojo;
import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import com.dihari.majduri.DihariMajduri.mobile.model.Labour;
import com.dihari.majduri.DihariMajduri.mobile.service.FarmerService;
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
    
    @Autowired
    private FarmerService farmerService;

    @GetMapping
    public ResponseEntity<?> getAllLabours() {
        List<Labour> labours = labourService.findAllLabours();
        List<LabourPojo> list = new ArrayList<>();
        for (Labour labour : labours) {
            LabourPojo labourResponse = new LabourPojo(labour.getId(), labour.getName(), labour.getMobileNumber(),labour.getFarmer().getId());
            list.add(labourResponse);
        }
        ResponseWrapper<List<LabourPojo>> responseWrapper = new ResponseWrapper<>(true, list);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLabourById(@PathVariable int id) {
        Optional<Labour> labourOptional = labourService.getLabourById(id);
        if (!labourOptional.isPresent()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"", ErrorCode.ID_NOT_EXISTS, "Labour Id  not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        Labour labour=labourOptional.get();
        LabourPojo labourResponse = new LabourPojo(labour.getId(), labour.getName(), labour.getMobileNumber(),labour.getFarmer().getId());
        ResponseWrapper<LabourPojo> responseWrapper = new ResponseWrapper<>(true, labourResponse);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping
    public ResponseEntity<?> addLabour(@Validated @RequestBody LabourPojo labourPojo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.VALIDATION_ERROR, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        
        Optional<Farmer> farmer=farmerService.getFarmerById(labourPojo.getFarmerId());
        if(!farmer.isPresent())
        {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.FARMER_ID_NOT_EXISTS, "Farmer Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        
        Labour labour=new Labour(labourPojo.getName(),labourPojo.getMobileNumber(),farmer.get());
        labourService.addLabour(labour);
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Labour added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(responseWrapper));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLabour(@PathVariable int id, @Validated @RequestBody LabourPojo updatedLabour, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.VALIDATION_ERROR, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }

        Optional<Farmer> farmer=farmerService.getFarmerById(updatedLabour.getFarmerId());
        if(!farmer.isPresent())
        {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.FARMER_ID_NOT_EXISTS, "Farmer Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }

        Labour labour=new Labour(updatedLabour.getId(),updatedLabour.getName(),updatedLabour.getMobileNumber(),farmer.get());
        if (!labourService.updateLabour(id, labour)) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"", ErrorCode.ID_NOT_EXISTS, "Labour Id does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Labour updated successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLabour(@PathVariable int id) {
        if (!labourService.deleteLabour(id)) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"", ErrorCode.ID_NOT_EXISTS, "Labour Id does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Labour deleted successfully");
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }
}
