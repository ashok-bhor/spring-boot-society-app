package com.javatpoint.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
//mark class as an Entity 
@Entity
//defining class name as Table name
@Table(name = "MaintenanceMasterEntry")	
public class MaintenanceMasterEntry {
	@Id
	private int flatNumber;
    private String currentYear;
    private double totalOutstanding;
    private double receivedTillNow;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date lastRecievedDate;
	public int getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}
	public String getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}
	public double getTotalOutstanding() {
		return totalOutstanding;
	}
	public void setTotalOutstanding(double totalOutstanding) {
		this.totalOutstanding = totalOutstanding;
	}
	public double getReceivedTillNow() {
		return receivedTillNow;
	}
	public void setReceivedTillNow(double receivedTillNow) {
		this.receivedTillNow = receivedTillNow;
	}
	public Date getLastRecievedDate() {
		return lastRecievedDate;
	}
	public void setLastRecievedDate(Date lastRecievedDate) {
		this.lastRecievedDate = lastRecievedDate;
	}
	
}
