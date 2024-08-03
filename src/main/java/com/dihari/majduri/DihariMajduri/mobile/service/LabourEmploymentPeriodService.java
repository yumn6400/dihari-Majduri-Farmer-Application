package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.dao.CropRepository;
import com.dihari.majduri.DihariMajduri.mobile.dao.CropWorkTypeRepository;
import com.dihari.majduri.DihariMajduri.mobile.dao.FarmerRepository;
import com.dihari.majduri.DihariMajduri.mobile.dao.LabourEmploymentPeriodRepository;
import com.dihari.majduri.DihariMajduri.mobile.dto.DihariRequest;
import com.dihari.majduri.DihariMajduri.mobile.dto.LabourEmploymentPeriodPojo;
import com.dihari.majduri.DihariMajduri.mobile.dto.LabourPojo;
import com.dihari.majduri.DihariMajduri.mobile.model.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.sql.Date;
@Service
public class LabourEmploymentPeriodService {

    @Autowired
    private LabourEmploymentPeriodRepository labourEmploymentPeriodRepository;

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private CropWorkTypeRepository cropWorkTypeRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    public List<LabourEmploymentPeriod> findAll() {
        return (List<LabourEmploymentPeriod>) labourEmploymentPeriodRepository.findAll();
    }

    public Optional<LabourEmploymentPeriod> findById(int id) {
        return labourEmploymentPeriodRepository.findById(id);
    }

    public void save(LabourEmploymentPeriod labourEmploymentPeriod) {
        labourEmploymentPeriodRepository.save(labourEmploymentPeriod);
    }

    public boolean update(int id, LabourEmploymentPeriod updatedLabourEmploymentPeriod) {
        Optional<LabourEmploymentPeriod> existingLabourEmploymentPeriodOptional = labourEmploymentPeriodRepository.findById(id);
        if (!existingLabourEmploymentPeriodOptional.isPresent()) {
            return false;
        }
        LabourEmploymentPeriod existingLabourEmploymentPeriod = existingLabourEmploymentPeriodOptional.get();
        existingLabourEmploymentPeriod.setDate(updatedLabourEmploymentPeriod.getDate());
        existingLabourEmploymentPeriod.setLabourCost(updatedLabourEmploymentPeriod.getLabourCost());
        existingLabourEmploymentPeriod.setFarmer(updatedLabourEmploymentPeriod.getFarmer());
        existingLabourEmploymentPeriod.setCrop(updatedLabourEmploymentPeriod.getCrop());
        existingLabourEmploymentPeriod.setCropWorkType(updatedLabourEmploymentPeriod.getCropWorkType());
        labourEmploymentPeriodRepository.save(existingLabourEmploymentPeriod);
        return true;
    }

    public boolean delete(int id) {
        Optional<LabourEmploymentPeriod> labourEmploymentPeriodOptional = labourEmploymentPeriodRepository.findById(id);
        if (!labourEmploymentPeriodOptional.isPresent()) {
            return false;
        }
        labourEmploymentPeriodRepository.delete(labourEmploymentPeriodOptional.get());
        return true;
    }

    public List<LabourEmploymentPeriodPojo> getLabourEmploymentPeriodsByFarmerId(int id) {
        List<LabourEmploymentPeriod> labourEmploymentPeriods = labourEmploymentPeriodRepository.findByFarmerId(id);

        Map<String, Map<String, Map<Date, List<LabourEmploymentPeriod>>>> groupedData = labourEmploymentPeriods.stream()
                .collect(Collectors.groupingBy(
                        lep -> lep.getCrop().getName(),
                        Collectors.groupingBy(
                                lep -> lep.getCropWorkType().getName(),
                                Collectors.groupingBy(LabourEmploymentPeriod::getDate)
                        )
                ));

        List<LabourEmploymentPeriodPojo> responseList = new ArrayList<>();

        groupedData.forEach((cropName, workTypeMap) -> {
            workTypeMap.forEach((workTypeName, dateMap) -> {
                dateMap.forEach((date, periods) -> {
                    List<LabourPojo> labourPojos = periods.stream()
                            .flatMap(lep -> lep.getLabourEmployments().stream())
                            .map(le -> new LabourPojo(le.getLabour().getId(), le.getLabour().getName(), le.getLabour().getMobileNumber()))
                            .collect(Collectors.toList());
                    LabourEmploymentPeriodPojo pojo = new LabourEmploymentPeriodPojo(date, cropName, workTypeName, labourPojos.size(), labourPojos);
                    responseList.add(pojo);
                });
            });
        });

        return responseList;
    }


    public String addLabourEmploymentPeriodByFarmerId(int farmerId, DihariRequest dihariRequest) throws ParseException {
        Crop crop = cropRepository.findByName(dihariRequest.getCropName());
        CropWorkType cropWorkType = cropWorkTypeRepository.findByName(dihariRequest.getCropWorkTypeName());
        Optional<Farmer> farmer= farmerRepository.findById(farmerId);

        String dateString = dihariRequest.getDate();
        if (dateString == null || !isValidDate(dateString)) {
            return new Gson().toJson(new ResponseWrapper(false, "Invalid or missing date. Date must be in dd/MM/yyyy format."));
        }

        if (crop == null || cropWorkType == null) {
            return new Gson().toJson(new ResponseWrapper(false, "Invalid crop or crop work type"));
        }

        if (farmer.isEmpty()) {
            return new Gson().toJson(new ResponseWrapper(false, "Farmer not found"));
        }

        Date employmentDate = convertStringToSqlDate(dihariRequest.getDate());

        LabourEmploymentPeriod labourEmploymentPeriod = new LabourEmploymentPeriod();
        labourEmploymentPeriod.setDate(employmentDate);
        labourEmploymentPeriod.setCrop(crop);
        labourEmploymentPeriod.setCropWorkType(cropWorkType);
        labourEmploymentPeriod.setFarmer(farmer.get());

        List<LabourEmployment> labourEmployments = new ArrayList<>();
        for (Labour labour : dihariRequest.getLaboursList()) {
            LabourEmployment labourEmployment = new LabourEmployment();
            labourEmployment.setLabour(labour);
            labourEmployment.setLabourEmploymentPeriod(labourEmploymentPeriod);
            labourEmployments.add(labourEmployment);
        }

        labourEmploymentPeriod.setLabourEmployments(labourEmployments);
        labourEmploymentPeriodRepository.save(labourEmploymentPeriod);

        return new Gson().toJson(new ResponseWrapper(true, "Labour Employment Period added successfully"));
    }

    private static Date convertStringToSqlDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date parsedDate = format.parse(dateString);
        return new Date(parsedDate.getTime());
    }

    private boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Set to strict parsing
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
