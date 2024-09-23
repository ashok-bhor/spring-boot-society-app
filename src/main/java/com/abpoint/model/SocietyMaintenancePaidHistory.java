package com.abpoint.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//mark class as an Entity 
@Entity
//defining class name as Table name
@Table(name = "SocietyMaintenancePaidHistory")
public class SocietyMaintenancePaidHistory {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
	private ApprovalPendingPayment approvalPendingPayment;

	private int flatNumber;
	private String year;
	private String flatType;
	private double annualMaintenance;
	private Date date;
	private String paymentMethod;
	private String transactionId;
	private double amount;
	private Boolean deleted = false;
	private String deletedReason;

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

	@Override
	public String toString() {
		return "SocietyMaintenancePaidHistory [flatNumber=" + flatNumber + ", year=" + year + ", flatType=" + flatType
				+ ", annualMaintenance=" + annualMaintenance + ", date=" + date + ", paymentMethod=" + paymentMethod
				+ ", transactionId=" + transactionId + ", amount=" + amount + "]";
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getDeletedReason() {
		return deletedReason;
	}

	public void setDeletedReason(String deletedReason) {
		this.deletedReason = deletedReason;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SocietyMaintenancePaidHistory that = (SocietyMaintenancePaidHistory) o;

		LocalDate thisDate = convertToLocalDate(this.date);
		LocalDate thatDate = convertToLocalDate(that.date);
		return flatNumber == that.flatNumber && Double.compare(that.annualMaintenance, annualMaintenance) == 0
				&& Double.compare(that.amount, amount) == 0 && Objects.equals(year, that.year)
				&& Objects.equals(flatType, that.flatType) && Objects.equals(thisDate, thatDate)
				&& Objects.equals(paymentMethod, that.paymentMethod)
				&& Objects.equals(transactionId, that.transactionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(flatNumber, year, flatType, annualMaintenance, date, paymentMethod, transactionId, amount);
	}

	private LocalDate convertToLocalDate(Date dateToConvert) {
		if (dateToConvert instanceof java.sql.Date) {
			return ((java.sql.Date) dateToConvert).toLocalDate();
		} else {
			return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
	}

}
