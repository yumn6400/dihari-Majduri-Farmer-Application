package com.dihari.majduri.DihariMajduri.mobile.service;

import com.dihari.majduri.DihariMajduri.mobile.dao.CropWorkTypeRepository;
import com.dihari.majduri.DihariMajduri.mobile.model.CropWorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CropWorkTypeService {

    @Autowired
    private CropWorkTypeRepository cropWorkTypeRepository;

    public List<CropWorkType> findAll() {
        return (List<CropWorkType>) cropWorkTypeRepository.findAll();
    }

    public Optional<CropWorkType> findById(int id) {
        return cropWorkTypeRepository.findById(id);
    }

    public void save(CropWorkType cropWorkType) {
        cropWorkTypeRepository.save(cropWorkType);
    }

    public boolean update(int id, CropWorkType updatedCropWorkType) {
        Optional<CropWorkType> existingCropWorkTypeOptional = cropWorkTypeRepository.findById(id);
        if (!existingCropWorkTypeOptional.isPresent()) {
            return false;
        }
        CropWorkType existingCropWorkType = existingCropWorkTypeOptional.get();
        existingCropWorkType.setName(updatedCropWorkType.getName());
        cropWorkTypeRepository.save(existingCropWorkType);
        return true;
    }

    public boolean delete(int id) {
        Optional<CropWorkType> cropWorkTypeOptional = cropWorkTypeRepository.findById(id);
        if (!cropWorkTypeOptional.isPresent()) {
            return false;
        }
        cropWorkTypeRepository.delete(cropWorkTypeOptional.get());
        return true;
    }
}
