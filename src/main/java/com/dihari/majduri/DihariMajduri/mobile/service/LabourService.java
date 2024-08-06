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
        return labourRepository.findAll();
    }

    public List<Labour> findAllLaboursByFarmerId(int farmerId) {

        return labourRepository.getByFarmerId(farmerId);
    }

    public Optional<Labour> getLabourById(int id) {
        return labourRepository.findById(id);
    }

    public void addLabour(Labour labour) {
        Optional<Labour> optionalLabour = labourRepository.findByMobileNumber(labour.getMobileNumber());
        if (optionalLabour.isPresent()) {
            Labour existingLabour = optionalLabour.get();
            existingLabour.setName(labour.getName());
            labourRepository.save(existingLabour);
        }
        else {
            labourRepository.save(labour);
        }
    }

    public boolean updateLabour(Labour updatedLabour) {
        Optional<Labour> optionalLabour = labourRepository.findById(updatedLabour.getId());
        if (optionalLabour.isPresent()) {
            Labour existingLabour = optionalLabour.get();

            existingLabour.setName(updatedLabour.getName());
            existingLabour.setMobileNumber(updatedLabour.getMobileNumber());
            existingLabour.setFarmer(updatedLabour.getFarmer());

            labourRepository.save(existingLabour);
            return true;
        }
        return false;
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
