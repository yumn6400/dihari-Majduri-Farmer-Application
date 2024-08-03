package com.dihari.majduri.DihariMajduri.mobile.dto;


import com.dihari.majduri.DihariMajduri.mobile.model.Labour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DihariRequest {
	private String cropName;
	private String cropWorkTypeName;
	private String date;
	private List<Labour> laboursList;


	
}
