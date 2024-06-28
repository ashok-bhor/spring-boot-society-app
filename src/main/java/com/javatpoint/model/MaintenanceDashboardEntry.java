package com.javatpoint.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class MaintenanceDashboardEntry {
    
    private String flatType;
    private double totalPaid;
    private String paidTillYear;
    private double outstanding;
    private double annualMaintenance;
	public String getFlatType() {
		return flatType;
	}
	public void setFlatType(String flatType) {
		this.flatType = flatType;
	}
	public double getTotalPaid() {
		return totalPaid;
	}
	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
	public String getPaidTillYear() {
		return paidTillYear;
	}
	public void setPaidTillYear(String paidTillYear) {
		this.paidTillYear = paidTillYear;
	}
	public double getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(double outstanding) {
		this.outstanding = outstanding;
	}
	public double getAnnualMaintenance() {
		return annualMaintenance;
	}
	public void setAnnualMaintenance(double annualMaintenance) {
		this.annualMaintenance = annualMaintenance;
	}
	@Override
	public String toString() {
		return "MaintenanceDashboardEntry [flatType=" + flatType + ", totalPaid=" + totalPaid
				+ ", paidTillYear=" + paidTillYear + ", outstanding=" + outstanding + ", annualMaintenance="
				+ annualMaintenance + "]";
	}
    
}