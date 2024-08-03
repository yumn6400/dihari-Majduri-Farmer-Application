package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.common.StatusCode;
import com.dihari.majduri.DihariMajduri.mobile.dao.FarmerRepository;
import com.dihari.majduri.DihariMajduri.mobile.dto.ChangeOwnerPin;
import com.dihari.majduri.DihariMajduri.mobile.dto.LoginRequest;
import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import com.dihari.majduri.DihariMajduri.mobile.common.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    public List<Farmer> findAllFarmers() {
        return (List<Farmer>) farmerRepository.findAll();
    }

    public Optional<Farmer> getFarmerById(int id) {
        return farmerRepository.findById(id);
    }

    public void addFarmer(Farmer farmer) {
        farmerRepository.save(farmer);
    }

    public boolean updateFarmer(int id, Farmer updatedFarmer) {
        Optional<Farmer> existingFarmerOptional = farmerRepository.findById(id);
        if (!existingFarmerOptional.isPresent()) {
            return false;
        }
        Farmer existingFarmer = existingFarmerOptional.get();
        existingFarmer.setName(updatedFarmer.getName());
        existingFarmer.setMobileNumber(updatedFarmer.getMobileNumber());
        farmerRepository.save(existingFarmer);
        return true;
    }

    public boolean deleteFarmer(int id) {
        Optional<Farmer> existingFarmerOptional = farmerRepository.findById(id);
        if (!existingFarmerOptional.isPresent()) {
            return false;
        }
        farmerRepository.delete(existingFarmerOptional.get());
        return true;
    }

    public ResponseWrapper validateFarmerPin(LoginRequest loginRequest) {
        Farmer farmer = farmerRepository.findByMobileNumber(loginRequest.getMobileNumber());

        if (farmer == null) {
            return new ResponseWrapper(false, StatusCode.MOBILE_NUMBER_NOT_EXISTS.getCode(), "Mobile number not exists");
        }
        if (farmer.getPin().equals(loginRequest.getPin())) {
            return new ResponseWrapper(true, StatusCode.AUTHENTICATE.getCode());
        } else {
            return new ResponseWrapper(false, StatusCode.NOT_AUTHENTICATE.getCode());
        }
    }

    public ResponseWrapper changeFarmerPin(ChangeOwnerPin changeOwnerPin) {
        Optional<Farmer> existingFarmerOptional = farmerRepository.findById(changeOwnerPin.getId());
        if (!existingFarmerOptional.isPresent()) {
            return new ResponseWrapper(false, StatusCode.ID_NOT_EXISTS.getCode(), "Farmer not found");
        }
        Farmer existingFarmer = existingFarmerOptional.get();
        existingFarmer.setPin(changeOwnerPin.getPin());
        farmerRepository.save(existingFarmer);
        return new ResponseWrapper(true, "Farmer Pin updated successfully");
    }
}
