package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.dao.*;

import com.dihari.majduri.DihariMajduri.mobile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return labourEmploymentPeriodRepository.findAll();
    }

    public Optional<LabourEmploymentPeriod> findById(int id) {
        return labourEmploymentPeriodRepository.findById(id);

    }

    public void save(LabourEmploymentPeriod labourEmploymentPeriod) {
        labourEmploymentPeriodRepository.save(labourEmploymentPeriod);
    }

    public List<LabourEmploymentPeriod> getLabourEmploymentPeriodsByFarmerId(int farmerId) {

        return labourEmploymentPeriodRepository.findByFarmerId(farmerId);
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

}
