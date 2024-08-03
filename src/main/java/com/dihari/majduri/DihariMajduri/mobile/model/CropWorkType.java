package com.dihari.majduri.DihariMajduri.mobile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cropWorkType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropWorkType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String name;



}
