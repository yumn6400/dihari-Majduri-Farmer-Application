package com.dihari.majduri.DihariMajduri.mobile.dao;

import com.dihari.majduri.DihariMajduri.mobile.model.Labour;
import com.dihari.majduri.DihariMajduri.mobile.model.LabourEmployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabourRepository extends JpaRepository<Labour,Integer> {
	Optional<Labour> findByMobileNumber(String mobileNumber);
}
