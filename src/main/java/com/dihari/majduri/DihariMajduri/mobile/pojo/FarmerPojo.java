package com.dihari.majduri.DihariMajduri.mobile.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerPojo {
    private int id;
    private String name;
    private String mobileNumber;
    private String pin;

    public FarmerPojo(int id , String name,String mobileNumber){
        this.id=id;
        this.name=name;
        this.mobileNumber=mobileNumber;
    }
    public FarmerPojo(String name,String mobileNumber){
        this.name=name;
        this.mobileNumber=mobileNumber;
    }
}
