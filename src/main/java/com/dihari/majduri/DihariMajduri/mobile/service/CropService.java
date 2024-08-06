package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.dao.CropRepository;
import com.dihari.majduri.DihariMajduri.mobile.model.Crop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    public List<Crop> findAll() {
        return cropRepository.findAll();
    }

    public Optional<Crop> findById(int id) {
        return cropRepository.findById(id);
    }

    public void save(Crop crop) {
        cropRepository.save(crop);
    }

    public boolean update(int id, Crop updatedCrop) {
        Optional<Crop> existingCropOptional = cropRepository.findById(id);
        if (!existingCropOptional.isPresent()) {
            return false;
        }
        Crop existingCrop = existingCropOptional.get();
        existingCrop.setName(updatedCrop.getName());
        cropRepository.save(existingCrop);
        return true;
    }

    public boolean delete(int id) {
        Optional<Crop> cropOptional = cropRepository.findById(id);
        if (!cropOptional.isPresent()) {
            return false;
        }
        cropRepository.delete(cropOptional.get());
        return true;
    }
}
