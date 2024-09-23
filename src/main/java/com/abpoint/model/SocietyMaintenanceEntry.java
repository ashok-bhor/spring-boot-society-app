package com.abpoint.model;

import org.springframework.stereotype.Service;

@Service
public class SocietyMaintenanceEntry {
	private Long id;
	private int flatNumber;
    private String flatType;
    private double totaloutstandingAmount;
	private double annualMaintenance;
    private double receivedTillNow;
    private String paidTillYear;
    
    
	public SocietyMaintenanceEntry() {
		super();
	}
	public SocietyMaintenanceEntry(MaintenanceMasterEntry maintenanceMasterEntry) {
		super();
		this.flatNumber = maintenanceMasterEntry.getFlatNumber();
		this.totaloutstandingAmount = maintenanceMasterEntry.getTotalOutstanding();
		this.receivedTillNow = maintenanceMasterEntry.getReceivedTillNow();
		this.paidTillYear = maintenanceMasterEntry.getCurrentYear();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}
	public String getFlatType() {
		return flatType;
	}
	public void setFlatType(String flatType) {
		this.flatType = flatType;
	}
	
	public double getTotaloutstandingAmount() {
		return totaloutstandingAmount;
	}
	public void setTotaloutstandingAmount(double totaloutstandingAmount) {
		this.totaloutstandingAmount = totaloutstandingAmount;
	}
	public double getAnnualMaintenance() {
		return annualMaintenance;
	}
	public void setAnnualMaintenance(double annualMaintenance) {
		this.annualMaintenance = annualMaintenance;
	}
	public double getReceivedTillNow() {
		return receivedTillNow;
	}
	public void setReceivedTillNow(double receivedTillNow) {
		this.receivedTillNow = receivedTillNow;
	}
	public String getPaidTillYear() {
		return paidTillYear;
	}
	public void setPaidTillYear(String paidTillYear) {
		this.paidTillYear = paidTillYear;
	}
	@Override
	public String toString() {
		return "SocietyMaintenanceEntry [flatNumber=" + flatNumber + ", flatType=" + flatType
				+ ", totaloutstandingAmount=" + totaloutstandingAmount + ", annualMaintenance=" + annualMaintenance
				+ ", receivedTillNow=" + receivedTillNow + ", paidTillYear=" + paidTillYear + "]";
	}
    
}
