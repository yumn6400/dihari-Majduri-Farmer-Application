package com.dihari.majduri.DihariMajduri.mobile.dao;

import com.dihari.majduri.DihariMajduri.mobile.model.CropWorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropWorkTypeRepository extends JpaRepository<CropWorkType,Integer> {
    CropWorkType findByName(String name);
}
