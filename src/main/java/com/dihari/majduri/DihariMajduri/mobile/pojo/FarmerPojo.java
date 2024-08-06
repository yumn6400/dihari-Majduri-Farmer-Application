package com.dihari.majduri.DihariMajduri.mobile.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerPojo {
    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String pin;

    public FarmerPojo(int id , String firstName,String lastName,String mobileNumber){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.mobileNumber=mobileNumber;
    }
    public FarmerPojo(String firstName,String lastName,String mobileNumber){
        this.firstName=firstName;
        this.lastName=lastName;
        this.mobileNumber=mobileNumber;
    }
}
