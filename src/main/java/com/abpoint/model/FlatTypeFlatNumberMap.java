package com.abpoint.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flat_type_flat_number_map")	
public class FlatTypeFlatNumberMap {
	@Id
	private int flatNumber;
	private String flatType;
	private double annualMaintenance;
	

	public String getFlatType() {
		return flatType;
	}

	public void setFlatType(String flatType) {
		this.flatType = flatType;
	}

	public int getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}

	public double getAnnualMaintenance() {
		return annualMaintenance;
	}
	
	

}