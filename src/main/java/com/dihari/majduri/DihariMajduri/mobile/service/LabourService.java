package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.dao.LabourRepository;

import com.dihari.majduri.DihariMajduri.mobile.model.Labour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LabourService {

    @Autowired
    private LabourRepository labourRepository;

    public List<Labour> findAllLabours() {
        return (List<Labour>) labourRepository.findAll();
    }

    public Optional<Labour> getLabourById(int id) {
        return labourRepository.findById(id);
    }

    public void addLabour(Labour labour) {
        labourRepository.save(labour);
    }

    public boolean updateLabour(int id, Labour updatedLabour) {
        Optional<Labour> existingLabourOptional = labourRepository.findById(id);
        if (!existingLabourOptional.isPresent()) {
            return false;
        }
        Labour existingLabour = existingLabourOptional.get();
        existingLabour.setName(updatedLabour.getName());
        existingLabour.setMobileNumber(updatedLabour.getMobileNumber());
        existingLabour.setFarmer(updatedLabour.getFarmer());
        existingLabour.setLabourEmployementPeriods(updatedLabour.getLabourEmployementPeriods());
        labourRepository.save(existingLabour);
        return true;
    }

    public boolean deleteLabour(int id) {
        Optional<Labour> labourOptional = labourRepository.findById(id);
        if (!labourOptional.isPresent()) {
            return false;
        }
        labourRepository.delete(labourOptional.get());
        return true;
    }
}
