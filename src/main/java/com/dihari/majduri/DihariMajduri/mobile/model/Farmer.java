package com.dihari.majduri.DihariMajduri.mobile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="farmer")
public class Farmer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Name cannot be blank")
    @Size(min=3, max=50, message="First Name length must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message="Name cannot be blank")
    @Size(min=3, max=50, message="Last Name length must be between 3 and 50 characters")
    private String lastName;

    @Column(unique=true)
    @NotBlank(message="Mobile number cannot be blank")
    @Pattern(regexp="[0-9]{10,15}", message="Mobile number should be between 10 to 15 digits")
    private String mobileNumber;

    @Size(min=4,message="Pin number should be of 4 characters")
    private String pin;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Labour> labours=new ArrayList<>();

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabourEmploymentPeriod> labourEmploymentPeriods=new ArrayList<>();


    public Farmer(String firstName,String lastName,String mobileNumber,String pin){
        this.firstName=firstName;
        this.lastName=lastName;
        this.mobileNumber=mobileNumber;
        this.pin=pin;
    }

    public Farmer(int id , String firstName,String lastName,String mobileNumber,String pin){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.mobileNumber=mobileNumber;
        this.pin=pin;
    }
}
