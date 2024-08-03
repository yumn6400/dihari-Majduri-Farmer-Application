package com.dihari.majduri.DihariMajduri.mobile.dao;

import com.dihari.majduri.DihariMajduri.mobile.model.LabourEmploymentPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabourEmploymentPeriodRepository extends JpaRepository<LabourEmploymentPeriod,Integer> {
    List<LabourEmploymentPeriod> findByFarmerId(int id);
}
