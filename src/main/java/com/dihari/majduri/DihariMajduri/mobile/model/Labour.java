package com.dihari.majduri.DihariMajduri.mobile.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="labour")
public class Labour {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Invalid Name")
    @Size(min=3,max=50,message="Name should be in between 3-50 characters")
    private String name;


    @NotBlank(message="Mobile number cannot be blank")
    @Pattern(regexp="[0-9]{10,15}", message="Mobile number should be between 10 to 15 digits")
    private String mobileNumber;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    @JsonBackReference
    private Farmer farmer;

    @OneToMany(mappedBy = "labour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabourEmployment> labourEmployments=new ArrayList<>();

    public Labour(String name,String mobileNumber,Farmer farmer){
        this.name=name;
        this.mobileNumber=mobileNumber;
        this.farmer=farmer;
    }
    public Labour(int id ,String name,String mobileNumber,Farmer farmer){
        this.id=id;
        this.name=name;
        this.mobileNumber=mobileNumber;
        this.farmer=farmer;
    }

}