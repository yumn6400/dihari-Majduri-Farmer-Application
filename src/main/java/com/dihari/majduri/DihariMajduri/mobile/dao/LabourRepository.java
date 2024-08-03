package com.dihari.majduri.DihariMajduri.mobile.dao;

import com.dihari.majduri.DihariMajduri.mobile.model.Labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabourRepository extends JpaRepository<Labour,Integer> {
	
}
