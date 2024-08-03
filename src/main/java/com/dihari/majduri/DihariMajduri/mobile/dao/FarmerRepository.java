package com.dihari.majduri.DihariMajduri.mobile.dao;

import com.dihari.majduri.DihariMajduri.mobile.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer,Integer>{
    Farmer findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumber(String mobileNumber);
}
