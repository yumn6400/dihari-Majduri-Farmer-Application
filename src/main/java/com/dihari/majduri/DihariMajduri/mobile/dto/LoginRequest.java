package com.dihari.majduri.DihariMajduri.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	    private String mobileNumber;
	    private String pin;
}
