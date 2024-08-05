package com.dihari.majduri.DihariMajduri.mobile.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabourEmploymentPeriodPojo {
		private Date date;	    
	    private CropPojo crop;
	    private CropWorkTypePojo cropWorkType;
	    private int labourCount;
	    private List<LabourPojo> labours;
		private FarmerPojo farmer;
	 
	}

