package com.javatpoint.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
//mark class as an Entity 
@Entity
//defining class name as Table name
@Table(name = "SocietyMaintenancePaidHistory")	
public class SocietyMaintenancePaidHistory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private int flatNumber;
    private String year;
    private String flatType;
	private double annualMaintenance;
    private Date date;
    private String paymentMethod;
    private String transactionId;
    private double amount;
    private boolean verified;
	public int getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getFlatType() {
		return flatType;
	}
	public void setFlatType(String flatType) {
		this.flatType = flatType;
	}
	public double getAnnualMaintenance() {
		return annualMaintenance;
	}
	public void setAnnualMaintenance(double annualMaintenance) {
		this.annualMaintenance = annualMaintenance;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	@Override
	public String toString() {
		return "SocietyMaintenancePaidHistory [flatNumber=" + flatNumber + ", year=" + year + ", flatType=" + flatType
				+ ", annualMaintenance=" + annualMaintenance + ", date=" + date + ", paymentMethod=" + paymentMethod
				+ ", transactionId=" + transactionId + ", amount=" + amount + ", verified=" + verified + "]";
	}
    
    
}
