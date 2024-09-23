package com.abpoint.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExtraChargesEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int flatNumber;
	private double chargedAmount;
	private String reason;
	private String comments;
	private String settled;

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(int flatNumberList) {
		this.flatNumber = flatNumberList;
	}

	public double getChargedAmount() {
		return chargedAmount;
	}

	public void setChargedAmount(double chargedAmount) {
		this.chargedAmount = chargedAmount;
	}

	public String getSettled() {
		return settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}


	@Override
	public String toString() {
		return "ExtraChargesEntry [id=" + id + ", flatNumber=" + flatNumber + ", chargedAmount=" + chargedAmount
				+ ", reason=" + reason + ", comments=" + comments + ", settled=" + settled + "]";
	}
	
	
}
