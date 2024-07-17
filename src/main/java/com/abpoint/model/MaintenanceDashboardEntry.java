package com.abpoint.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class MaintenanceDashboardEntry {

	private String flatType;
	private double totalPaid;
	private String paidTillYear;
	private double maintenanceOutstanding;
	private double annualMaintenance;
	private double extraCharges;
	private double totalOutstanding;

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

	public double getMaintenanceOutstanding() {
		return maintenanceOutstanding;
	}

	public void setMaintenanceOutstanding(double maintenanceOutstanding) {
		this.maintenanceOutstanding = maintenanceOutstanding;
	}

	public double getAnnualMaintenance() {
		return annualMaintenance;
	}

	public void setAnnualMaintenance(double annualMaintenance) {
		this.annualMaintenance = annualMaintenance;
	}

	public double getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(double extraCharges) {
		this.extraCharges = extraCharges;
	}

	public double getTotalOutstanding() {
		return totalOutstanding;
	}

	public void setTotalOutstanding(double totalOutstanding) {
		this.totalOutstanding = totalOutstanding;
	}

	@Override
	public String toString() {
		return "MaintenanceDashboardEntry [flatType=" + flatType + ", totalPaid=" + totalPaid + ", paidTillYear="
				+ paidTillYear + ", maintenanceOutstanding=" + maintenanceOutstanding + ", annualMaintenance="
				+ annualMaintenance + ", extraCharges=" + extraCharges + ", totalOutstanding=" + totalOutstanding + "]";
	}

}