package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.common.ErrorCode;
import com.dihari.majduri.DihariMajduri.mobile.dao.FarmerRepository;
import com.dihari.majduri.DihariMajduri.mobile.pojo.ChangeFarmerPinPojo;
import com.dihari.majduri.DihariMajduri.mobile.pojo.LoginRequestPojo;
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
        return farmerRepository.findAll();
    }

    public Optional<Farmer> getFarmerById(int id) {
        return farmerRepository.findById(id);
    }

    public int addFarmer(Farmer farmer) {
        Optional<Farmer> existingFarmerOptional = farmerRepository.findByMobileNumber(farmer.getMobileNumber());
        if (existingFarmerOptional.isPresent()) {
           Farmer existingFarmer=existingFarmerOptional.get();
           existingFarmer.setFirstName(farmer.getFirstName());
            existingFarmer.setLastName(farmer.getLastName());
           existingFarmer.setMobileNumber(farmer.getMobileNumber());
           existingFarmer.setPin(farmer.getPin());
           farmerRepository.save(existingFarmer);
           return existingFarmer.getId();
        }
        else {
            farmerRepository.save(farmer);
            return farmer.getId();
        }
    }

    public boolean updateFarmer(Farmer updatedFarmer) {
        Optional<Farmer> existingFarmerOptional = farmerRepository.findById(updatedFarmer.getId());
        if (existingFarmerOptional.isEmpty()) {
            return false;
        }
        Farmer existingFarmer = existingFarmerOptional.get();
        existingFarmer.setFirstName(updatedFarmer.getFirstName());
        existingFarmer.setLastName(updatedFarmer.getLastName());
        existingFarmer.setMobileNumber(updatedFarmer.getMobileNumber());
        existingFarmer.setPin(updatedFarmer.getPin());
        farmerRepository.save(existingFarmer);
        return true;
    }

    public boolean deleteFarmer(int id) {
        Optional<Farmer> existingFarmerOptional = farmerRepository.findById(id);
        if (existingFarmerOptional.isEmpty()) {
            return false;
        }
        farmerRepository.delete(existingFarmerOptional.get());
        return true;
    }

    public ResponseWrapper<String> validateFarmerPin(LoginRequestPojo loginRequestPojo) {
        Optional<Farmer> farmerOptional = farmerRepository.findByMobileNumber(loginRequestPojo.getMobileNumber());

        if (farmerOptional.isEmpty()) {
            return new ResponseWrapper<>(false, "", ErrorCode.MOBILE_NUMBER_NOT_EXISTS, "Mobile number not exists");
        }
        Farmer farmer=farmerOptional.get();
        if (farmer.getPin().equals(loginRequestPojo.getPin())) {
            return new ResponseWrapper<>(true, "Authenticate successfully");
        } else {
            return new ResponseWrapper<>(false, "", ErrorCode.NOT_AUTHENTICATE, "Invalid Pin");
        }
    }

    public ResponseWrapper<String> changeFarmerPin(ChangeFarmerPinPojo changeOwnerPin) {
        Optional<Farmer> existingFarmerOptional = farmerRepository.findById(changeOwnerPin.getId());
        if (existingFarmerOptional.isEmpty()) {
            return new ResponseWrapper<>(false, "", ErrorCode.ID_NOT_EXISTS, "Farmer not found");
        }
        Farmer existingFarmer = existingFarmerOptional.get();
        existingFarmer.setPin(changeOwnerPin.getPin());
        farmerRepository.save(existingFarmer);
        return new ResponseWrapper<>(true, "Farmer Pin updated successfully");
    }

    public boolean existsByMobileNumber(String mobileNumber){
        return farmerRepository.existsByMobileNumber(mobileNumber);
    }
}
