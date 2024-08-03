package com.dihari.majduri.DihariMajduri.mobile.dto;


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
	    private String cropName;
	    private String cropWorkTypeName;
	    private int labourCount;
	    private List<LabourPojo> labourPojo;

	 
	}

