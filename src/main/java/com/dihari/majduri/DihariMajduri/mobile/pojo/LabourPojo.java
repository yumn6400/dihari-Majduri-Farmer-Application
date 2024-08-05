package com.dihari.majduri.DihariMajduri.mobile.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabourPojo {

	private int id;
	private String name;
	private String mobileNumber;
	private int farmerId;
}
