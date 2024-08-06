package com.dihari.majduri.DihariMajduri.mobile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
	@Table(name="labour_employment_period")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class LabourEmploymentPeriod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date date;

	@ManyToOne
	@JoinColumn(name = "crop_id")
	private Crop crop;

	@ManyToOne
	@JoinColumn(name = "cropWorkType_id")
	private CropWorkType cropWorkType;

	@ManyToOne
	@JoinColumn(name = "farmer_id")
	private Farmer farmer;

	//CascadeType.ALL: This specifies that all operations (PERSIST, MERGE, REMOVE, REFRESH, DETACH) should be cascaded from the parent to the child entities. When you delete the parent entity, the child entities will also be deleted.

    //orphanRemoval = true: This ensures that if a child is removed from the parent's collection of children, the child entity will be deleted from the database.

	@OneToMany(mappedBy = "labourEmploymentPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LabourEmployment> labourEmployments=new ArrayList<>();


}
