package com.dihari.majduri.DihariMajduri.mobile.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabourEmploymentPeriodPojo {
		private String date;
	    private CropPojo crop;
	    private CropWorkTypePojo cropWorkType;
	    private int labourCount;
	    private List<LabourPojo> labours;
		private FarmerPojo farmer;
	 
	}

