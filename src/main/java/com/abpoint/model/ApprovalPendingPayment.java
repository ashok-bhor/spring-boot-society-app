package com.abpoint.model;



import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//mark class as an Entity 
@Entity
//defining class name as Table name
@Table(name = "ApprovalPendingPayments")	
public class ApprovalPendingPayment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private int flatNumber;
    private Date date;
    private String paymentMethod;
    private String transactionId;
    private double amount;
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
	@Override
	public String toString() {
		return "ApprovalPendingPayment [id=" + id + ", flatNumber=" + flatNumber + ", date=" + date + ", paymentMethod="
				+ paymentMethod + ", transactionId=" + transactionId + ", amount=" + amount + "]";
	}
    
    
}
