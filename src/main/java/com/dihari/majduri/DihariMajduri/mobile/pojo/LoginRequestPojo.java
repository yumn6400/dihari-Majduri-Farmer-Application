package com.dihari.majduri.DihariMajduri.mobile.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestPojo {
	    private String mobileNumber;
	    private String pin;
}
