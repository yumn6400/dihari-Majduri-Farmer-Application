package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import com.dihari.majduri.DihariMajduri.mobile.dao.*;

import com.dihari.majduri.DihariMajduri.mobile.model.*;
import com.dihari.majduri.DihariMajduri.mobile.pojo.*;
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

    @Autowired
    private LabourRepository labourRepository;

    public List<LabourEmploymentPeriod> findAll() {
        List<LabourEmploymentPeriod> labourEmploymentPeriods = (List<LabourEmploymentPeriod>) labourEmploymentPeriodRepository.findAll();
        return labourEmploymentPeriods;
    }

    public Optional<LabourEmploymentPeriod> findById(int id) {
        Optional<LabourEmploymentPeriod> labourEmploymentPeriod=labourEmploymentPeriodRepository.findById(id);
        return labourEmploymentPeriod;

    }

    public void save(LabourEmploymentPeriod labourEmploymentPeriod) {
        labourEmploymentPeriodRepository.save(labourEmploymentPeriod);
    }

    public List<LabourEmploymentPeriod> getLabourEmploymentPeriodsByFarmerId(int farmerId) {

        List<LabourEmploymentPeriod> labourEmploymentPeriods = labourEmploymentPeriodRepository.findByFarmerId(farmerId);
        return labourEmploymentPeriods;
    }


    public boolean delete(int id){
        Optional<LabourEmploymentPeriod> labourEmploymentPeriod=findById(id);
        if(labourEmploymentPeriod.isEmpty())
        {
            return false;
        }
        labourEmploymentPeriodRepository.deleteById(id);
        return true;
    }


//    private static Date convertStringToSqlDate(String dateString) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        java.util.Date parsedDate = format.parse(dateString);
//        return new Date(parsedDate.getTime());
//    }
//
//    private boolean isValidDate(String dateStr) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            sdf.setLenient(false); // Set to strict parsing
//            sdf.parse(dateStr);
//            return true;
//        } catch (ParseException e) {
//            return false;
//        }
//    }
}
