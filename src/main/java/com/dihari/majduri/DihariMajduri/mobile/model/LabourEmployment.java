package com.dihari.majduri.DihariMajduri.mobile.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "labourEmployment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabourEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "labour_id")
    private Labour labour;

    @ManyToOne
    @JoinColumn(name = "labourEmploymentPeriod_id")
    private LabourEmploymentPeriod labourEmploymentPeriod;

}
