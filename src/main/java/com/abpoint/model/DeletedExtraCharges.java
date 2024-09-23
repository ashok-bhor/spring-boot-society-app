package com.abpoint.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class DeletedExtraCharges {

	@Id
	private Long id;
	
	
	@OneToOne
	@JoinColumn(name = "id")
	private ExtraChargesEntry chargesEntry;
	
	private int flatNumber;
	private double chargedAmount;
	private String reason;
	private String comments;
	private String deleteReasonSelect;
	private String deleteReasonOther;

	public DeletedExtraCharges() {
		// TODO Auto-generated constructor stub
	}

	public DeletedExtraCharges(ExtraChargesEntry extraChargesEntry, String deleteReasonSelect,
			String deleteReasonOther) {
		super();
		this.id = extraChargesEntry.getId();
		this.flatNumber = extraChargesEntry.getFlatNumber();
		this.chargedAmount = extraChargesEntry.getChargedAmount();
		this.reason = extraChargesEntry.getReason();
		this.comments = extraChargesEntry.getComments();
		this.deleteReasonSelect = deleteReasonSelect;
		this.deleteReasonOther = deleteReasonOther;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return chargedAmount;
	}

	public void setAmount(double amount) {
		this.chargedAmount = amount;
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

	public String getDeleteReasonSelect() {
		return deleteReasonSelect;
	}

	public void setDeleteReasonSelect(String deleteReasonSelect) {
		this.deleteReasonSelect = deleteReasonSelect;
	}

	public String getDeleteReasonOther() {
		return deleteReasonOther;
	}

	public void setDeleteReasonOther(String deleteReasonOther) {
		this.deleteReasonOther = deleteReasonOther;
	}

	@Override
	public String toString() {
		return "DeletedExtraCharges [id=" + id + ", flatNumber=" + flatNumber + ", chargedAmount=" + chargedAmount
				+ ", reason=" + reason + ", comments=" + comments + ", deleteReasonSelect=" + deleteReasonSelect
				+ ", deleteReasonOther=" + deleteReasonOther + "]";
	}

}
