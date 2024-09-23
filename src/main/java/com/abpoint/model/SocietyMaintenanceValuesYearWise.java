package com.abpoint.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "society_maintenance_values_year_wise")
public class SocietyMaintenanceValuesYearWise {
    @Id
    private int recordNum;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "flat_type")
    private String flatType;

    @Column(name = "maintenance_value")
    private double maintenanceValue;

	
	public int getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public String getFlatType() {
		return flatType;
	}
	public void setFlatType(String flatType) {
		this.flatType = flatType;
	}
	public double getMaintenanceValue() {
		return maintenanceValue;
	}
	public void setMaintenanceValue(double maintenanceValue) {
		this.maintenanceValue = maintenanceValue;
	}

	
}

