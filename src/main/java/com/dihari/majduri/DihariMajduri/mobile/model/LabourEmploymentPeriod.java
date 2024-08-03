package com.dihari.majduri.DihariMajduri.mobile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
	@Table(name="employeeEmploymentPeriod")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class LabourEmploymentPeriod {
		@Id
		@GeneratedValue(strategy= GenerationType.AUTO)
		private int id;
		private Date date;
		private BigDecimal labourCost;

		@ManyToOne
		@JoinColumn(name = "farmer_id")
		private Farmer farmer;

		@ManyToOne
		@JoinColumn(name = "crop_id")
		private Crop crop;

		@ManyToOne
		@JoinColumn(name = "cropWorkType_id")
		private CropWorkType cropWorkType;

		@OneToMany(mappedBy = "labourEmploymentPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
		private List<LabourEmployment> labourEmployments=new ArrayList<>();

		@ManyToOne
		@JoinColumn(name = "labour_id")
		private Labour labour;


}
