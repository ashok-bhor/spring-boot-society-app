package com.abpoint.model;

public class MaintenanceDashboardCard {

	private String header;
	private String value;
	private String cardColor;
	private boolean extraChargesLink;

	// Default constructor
	public MaintenanceDashboardCard() {
	}

	// Parameterized constructor
	public MaintenanceDashboardCard(String header, String value, String cardColor, boolean extraChargesLink) {
		this.header = header;
		this.value = value;
		this.cardColor = cardColor;
		this.extraChargesLink = extraChargesLink;
	}

	// Getters and Setters
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCardColor() {
		return cardColor;
	}

	public void setCardColor(String cardColor) {
		this.cardColor = cardColor;
	}

	public boolean isExtraChargesLink() {
		return extraChargesLink;
	}

	public void setExtraChargesLink(boolean extraChargesLink) {
		this.extraChargesLink = extraChargesLink;
	}

	@Override
	public String toString() {
		return "MaintenanceDashboardCard{" + "header='" + header + '\'' + ", value='" + value + '\'' + ", cardColor='"
				+ cardColor + '\'' + ", extraChargesLink=" + extraChargesLink + '}';
	}
}
