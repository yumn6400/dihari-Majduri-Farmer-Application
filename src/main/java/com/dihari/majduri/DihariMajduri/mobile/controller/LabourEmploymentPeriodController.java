package com.dihari.majduri.DihariMajduri.mobile.controller;

import com.dihari.majduri.DihariMajduri.mobile.common.ErrorCode;
import com.dihari.majduri.DihariMajduri.mobile.common.Helper;
import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.model.*;
import com.dihari.majduri.DihariMajduri.mobile.pojo.*;
import com.dihari.majduri.DihariMajduri.mobile.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/labour-employment-periods")
public class LabourEmploymentPeriodController {

    @Autowired
    private LabourEmploymentPeriodService labourEmploymentPeriodService;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private CropService cropService;

    @Autowired
    private LabourService labourService;

    @Autowired
    private CropWorkTypeService cropWorkTypeService;
    @GetMapping
    public ResponseEntity<String> getAllLabourEmploymentPeriods() {
        List<LabourEmploymentPeriod> labourEmploymentPeriods = labourEmploymentPeriodService.findAll();
        Map<Integer, Map<Integer, Map<Date, List<LabourEmploymentPeriod>>>> groupedData = labourEmploymentPeriods.stream()
                .collect(Collectors.groupingBy(
                        lep -> lep.getCrop().getId(),
                        Collectors.groupingBy(
                                lep -> lep.getCropWorkType().getId(),
                                Collectors.groupingBy(LabourEmploymentPeriod::getDate)
                        )
                ));
        List<LabourEmploymentPeriodPojo> responseList = new ArrayList<>();
        groupedData.forEach((cropId, workTypeMap) -> workTypeMap.forEach((workTypeId, dateMap) -> {
            dateMap.forEach((date, periods) -> {
                List<LabourPojo> labourPojos = periods.stream()
                        .flatMap(lep -> lep.getLabourEmployments().stream())
                        .map(le -> new LabourPojo(
                                le.getLabour().getId(),
                                le.getLabour().getName(),
                                le.getLabour().getMobileNumber(),
                                le.getLabour().getFarmer().getId()))
                        .collect(Collectors.toList());

                CropPojo cropPojo = new CropPojo(periods.get(0).getCrop().getId(), periods.get(0).getCrop().getName());
                CropWorkTypePojo cropWorkTypePojo = new CropWorkTypePojo(periods.get(0).getCropWorkType().getId(), periods.get(0).getCropWorkType().getName());
                FarmerPojo farmerPojo = new FarmerPojo(periods.get(0).getFarmer().getId(), periods.get(0).getFarmer().getFirstName(),periods.get(0).getFarmer().getLastName(), periods.get(0).getFarmer().getMobileNumber(), periods.get(0).getFarmer().getPin());

                LabourEmploymentPeriodPojo pojo = new LabourEmploymentPeriodPojo(Helper.convertSqlDateToString(date), cropPojo, cropWorkTypePojo, labourPojos.size(), labourPojos, farmerPojo);
                responseList.add(pojo);
            });
        }));




        ResponseWrapper<List<LabourEmploymentPeriodPojo>> responseWrapper = new ResponseWrapper<>(true, responseList);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getLabourEmploymentPeriodById(@PathVariable int id) {
        Optional<LabourEmploymentPeriod> labourEmploymentPeriodOptional = labourEmploymentPeriodService.findById(id);

        if (labourEmploymentPeriodOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.ID_NOT_EXISTS, "Labour Employment Period not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }

        LabourEmploymentPeriod labourEmploymentPeriod = labourEmploymentPeriodOptional.get();

        List<LabourPojo> labourPojos = labourEmploymentPeriod.getLabourEmployments().stream()
                .map(le -> new LabourPojo(
                        le.getLabour().getId(),
                        le.getLabour().getName(),
                        le.getLabour().getMobileNumber(),
                        le.getLabour().getFarmer().getId()))
                .collect(Collectors.toList());

        CropPojo cropPojo = labourEmploymentPeriod.getCrop() != null ?
                new CropPojo(labourEmploymentPeriod.getCrop().getId(), labourEmploymentPeriod.getCrop().getName()) :
                new CropPojo(0, "Unknown");

        CropWorkTypePojo cropWorkTypePojo = labourEmploymentPeriod.getCropWorkType() != null ?
                new CropWorkTypePojo(labourEmploymentPeriod.getCropWorkType().getId(), labourEmploymentPeriod.getCropWorkType().getName()) :
                new CropWorkTypePojo(0, "Unknown");

        FarmerPojo farmerPojo = labourEmploymentPeriod.getFarmer() != null ?
                new FarmerPojo(labourEmploymentPeriod.getFarmer().getId(), labourEmploymentPeriod.getFarmer().getFirstName(),labourEmploymentPeriod.getFarmer().getLastName(), labourEmploymentPeriod.getFarmer().getMobileNumber(), labourEmploymentPeriod.getFarmer().getPin()) :
                new FarmerPojo(0, "Unknown", "Unknown", "Unknown");

        LabourEmploymentPeriodPojo labourEmploymentPeriodPojo = new LabourEmploymentPeriodPojo(
                Helper.convertSqlDateToString(labourEmploymentPeriod.getDate()),
                cropPojo,
                cropWorkTypePojo,
                labourPojos.size(),
                labourPojos,
                farmerPojo
        );

        ResponseWrapper<LabourEmploymentPeriodPojo> responseWrapper = new ResponseWrapper<>(true, labourEmploymentPeriodPojo);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @GetMapping("/farmerId/{id}")
    public ResponseEntity<String> getLabourEmploymentPeriodsByFarmerId(@PathVariable int id) {
        List<LabourEmploymentPeriod> labourEmploymentPeriods = labourEmploymentPeriodService.getLabourEmploymentPeriodsByFarmerId(id);

        Map<Integer, Map<Integer, Map<Date, List<LabourEmploymentPeriod>>>> groupedData = labourEmploymentPeriods.stream()
                .collect(Collectors.groupingBy(
                        lep -> lep.getCrop().getId(),
                        Collectors.groupingBy(
                                lep -> lep.getCropWorkType().getId(),
                                Collectors.groupingBy(LabourEmploymentPeriod::getDate)
                        )
                ));

        List<LabourEmploymentPeriodPojo> responseList = new ArrayList<>();

        groupedData.forEach((cropId, workTypeMap) -> workTypeMap.forEach((workTypeId, dateMap) -> {
            dateMap.forEach((date, periods) -> {
                List<LabourPojo> labourPojos = periods.stream()
                        .flatMap(lep -> lep.getLabourEmployments().stream())
                        .map(le -> new LabourPojo(
                                le.getLabour().getId(),
                                le.getLabour().getName(),
                                le.getLabour().getMobileNumber(),
                                le.getLabour().getFarmer().getId()))
                        .collect(Collectors.toList());

                CropPojo cropPojo = new CropPojo(periods.get(0).getCrop().getId(), periods.get(0).getCrop().getName());
                CropWorkTypePojo cropWorkTypePojo = new CropWorkTypePojo(periods.get(0).getCropWorkType().getId(), periods.get(0).getCropWorkType().getName());
                FarmerPojo farmerPojo = new FarmerPojo(periods.get(0).getFarmer().getId(), periods.get(0).getFarmer().getFirstName(),periods.get(0).getFarmer().getLastName(), periods.get(0).getFarmer().getMobileNumber(), periods.get(0).getFarmer().getPin());

                LabourEmploymentPeriodPojo pojo = new LabourEmploymentPeriodPojo(Helper.convertSqlDateToString(date), cropPojo, cropWorkTypePojo, labourPojos.size(), labourPojos, farmerPojo);
                responseList.add(pojo);
            });
        }));
        ResponseWrapper<List<LabourEmploymentPeriodPojo>> responseWrapper = new ResponseWrapper<>(true, responseList);
        return ResponseEntity.ok(new Gson().toJson(responseWrapper));
    }

    @PostMapping
    public ResponseEntity<String> addLabourEmploymentPeriod(
            @Validated @RequestBody LabourEmploymentPeriodPojo labourEmploymentPeriodPojo, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.VALIDATION_ERROR, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }

        LabourEmploymentPeriod labourEmploymentPeriod = new LabourEmploymentPeriod();
        labourEmploymentPeriod.setDate(Helper.convertStringToSqlDate(labourEmploymentPeriodPojo.getDate()));

        Optional<Crop> cropOptional = cropService.findById(labourEmploymentPeriodPojo.getCrop().getId());
        if (cropOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.CROP_ID_NOT_EXISTS, "Crop Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        Crop crop = cropOptional.get();

        Optional<CropWorkType> cropWorkTypeOptional = cropWorkTypeService.findById(labourEmploymentPeriodPojo.getCropWorkType().getId());
        if (cropWorkTypeOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.CROP_WORK_TYPE_ID_NOT_EXISTS, "Crop work type Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        CropWorkType cropWorkType = cropWorkTypeOptional.get();

        Optional<Farmer> farmerOptional=farmerService.getFarmerById(labourEmploymentPeriodPojo.getFarmer().getId());
        if(farmerOptional.isEmpty()){
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.FARMER_ID_NOT_EXISTS, "farmer Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        Farmer farmer=farmerOptional.get();

        labourEmploymentPeriod.setCrop(crop);
        labourEmploymentPeriod.setCropWorkType(cropWorkType);
        labourEmploymentPeriod.setFarmer(farmer);

        List<LabourEmployment> labourEmployments = new ArrayList<>();
        for (LabourPojo labourPojo : labourEmploymentPeriodPojo.getLabours()) {
            Optional<Labour> labourOptional = labourService.getLabourById(labourPojo.getId());
            if (labourOptional.isEmpty()) {
                ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.LABOUR_ID_NOT_EXISTS, "Labour with ID: " + labourPojo.getId() + " not exists.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
            }
            LabourEmployment labourEmployment = new LabourEmployment();
            labourEmployment.setLabour(labourOptional.get());
            labourEmployment.setLabourEmploymentPeriod(labourEmploymentPeriod);
            labourEmployments.add(labourEmployment);
        }

        labourEmploymentPeriod.setLabourEmployments(labourEmployments);
        labourEmploymentPeriodService.save(labourEmploymentPeriod);

        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Labour Employment Period added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(responseWrapper));
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateLabourEmploymentPeriod(
            @PathVariable int id,
            @Validated @RequestBody LabourEmploymentPeriodPojo updatedLabourEmploymentPeriodPojo,
            BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasErrors()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.VALIDATION_ERROR, "Validation error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }

        Optional<LabourEmploymentPeriod> labourEmploymentPeriodOptional = labourEmploymentPeriodService.findById(id);
        if (labourEmploymentPeriodOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.ID_NOT_EXISTS, "Labour Employment Period not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }

        LabourEmploymentPeriod labourEmploymentPeriod = labourEmploymentPeriodOptional.get();
        labourEmploymentPeriod.setDate(Helper.convertStringToSqlDate(updatedLabourEmploymentPeriodPojo.getDate()));

        Optional<Crop> cropOptional = cropService.findById(updatedLabourEmploymentPeriodPojo.getCrop().getId());
        if (cropOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.CROP_ID_NOT_EXISTS, "Crop Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        Crop crop = cropOptional.get();

        Optional<CropWorkType> cropWorkTypeOptional = cropWorkTypeService.findById(updatedLabourEmploymentPeriodPojo.getCropWorkType().getId());
        if (cropWorkTypeOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.CROP_WORK_TYPE_ID_NOT_EXISTS, "Crop work type Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        CropWorkType cropWorkType = cropWorkTypeOptional.get();

        Optional<Farmer> farmerOptional = farmerService.getFarmerById(updatedLabourEmploymentPeriodPojo.getFarmer().getId());
        if (farmerOptional.isEmpty()) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.FARMER_ID_NOT_EXISTS, "Farmer Id not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
        }
        Farmer farmer = farmerOptional.get();

        labourEmploymentPeriod.setCrop(crop);
        labourEmploymentPeriod.setCropWorkType(cropWorkType);
        labourEmploymentPeriod.setFarmer(farmer);

        List<LabourEmployment> labourEmployments = new ArrayList<>();
        for (LabourPojo labourPojo : updatedLabourEmploymentPeriodPojo.getLabours()) {
            Optional<Labour> labourOptional = labourService.getLabourById(labourPojo.getId());
            if (labourOptional.isEmpty()) {
                ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false, "", ErrorCode.LABOUR_ID_NOT_EXISTS, "Labour with ID: " + labourPojo.getId() + " not exists.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Gson().toJson(responseWrapper));
            }
            LabourEmployment labourEmployment = new LabourEmployment();
            labourEmployment.setLabour(labourOptional.get());
            labourEmployment.setLabourEmploymentPeriod(labourEmploymentPeriod);
            labourEmployments.add(labourEmployment);
        }

        // Clear the existing labourEmployments collection before adding the new elements
        labourEmploymentPeriod.getLabourEmployments().clear();
        labourEmploymentPeriod.getLabourEmployments().addAll(labourEmployments);

        labourEmploymentPeriodService.save(labourEmploymentPeriod);

        ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Labour Employment Period updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(responseWrapper));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLabourEmploymentPeriod(@PathVariable int id) {
        boolean deleted = labourEmploymentPeriodService.delete(id);
        if (deleted) {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(true, "Labour Employment Period deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(responseWrapper));
        } else {
            ResponseWrapper<String> responseWrapper = new ResponseWrapper<>(false,"",ErrorCode.ID_NOT_EXISTS, "Labour Employment Period Id not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Gson().toJson(responseWrapper));
        }
    }



}
