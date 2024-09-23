package com.abpoint.model;

public class ExtraChargesEntryForAllFlats {

	private String selectedString;
	private double chargedAmount;
	private String reason;
	private String comments;

	public String getSelectedString() {
		return selectedString;
	}

	public void setSelectedString(String selectedString) {
		this.selectedString = selectedString;
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


	public double getChargedAmount() {
		return chargedAmount;
	}

	public void setChargedAmount(double chargedAmount) {
		this.chargedAmount = chargedAmount;
	}

	@Override
	public String toString() {
		return "ExtraChargesEntryForAllFlats [flatNumber=" + selectedString + ", chargedAmount="
				+ chargedAmount + ", reason=" + reason + ", comments=" + comments + "]";
	}

}
