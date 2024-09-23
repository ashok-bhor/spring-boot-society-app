package com.abpoint.model.reciept;

public class GenerateReceipt {

	private int flatNumber;
	private double outstandingAmount;

	public GenerateReceipt() {
		super();
		this.flatNumber = flatNumber;
		this.outstandingAmount = outstandingAmount;
	}
	public int getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}
	public double getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	@Override
	public String toString() {
		return "GridData [flatNumber=" + flatNumber + ", outstandingAmount=" + outstandingAmount + "]";
	}
	
	
	
}
