package com.abpoint.model;

public class ExtraChargesRequest {
	private ExtraChargesEntry ece;
	private Double oldCharges;

	public ExtraChargesEntry getEce() {
		return ece;
	}

	public void setEce(ExtraChargesEntry ece) {
		this.ece = ece;
	}

	public Double getOldCharges() {
		return oldCharges;
	}

	public void setOldCharges(Double oldCharges) {
		this.oldCharges = oldCharges;
	}

	@Override
	public String toString() {
		return "ExtraChargesRequest [ece=" + ece + ", oldCharges=" + oldCharges + "]";
	}

}
