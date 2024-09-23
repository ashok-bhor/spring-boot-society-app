package com.abpoint.model;



import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//mark class as an Entity 
@Entity
//defining class name as Table name
@Table(name = "ActionedPaymentsDetails")	
public class ActionedPaymentsDetails {
	@Id
    private Long id;
	
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private ApprovalPendingPayment approvalPendingPayment;

	private int flatNumber;
    private Date date;
    private String paymentMethod;
    private String transactionId;
    private double amount;
    private String rejectReason;
    private String action;
    private String actionedBy;
    private String actionedTimestamp;
    private String actionersUserAgentDetails;
    
	public ActionedPaymentsDetails() {
		super();
	}
	public ActionedPaymentsDetails(ApprovalPendingPayment approvalPendingPayment,String action, String rejectReason,String actionedBy, String actionedTimestamp,String actionersUserAgentDetails) {
		super();
		this.id = approvalPendingPayment.getId();
		this.flatNumber = approvalPendingPayment.getFlatNumber();
		this.date = approvalPendingPayment.getDate();
		this.paymentMethod = approvalPendingPayment.getPaymentMethod();
		this.transactionId = approvalPendingPayment.getPaymentMethod();
		this.amount = approvalPendingPayment.getAmount();
		this.action = action;
		this.rejectReason = rejectReason;
		this.actionedBy =actionedBy;
		this.actionedTimestamp =actionedTimestamp;
		this.actionersUserAgentDetails = actionersUserAgentDetails;
	}
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getActionedBy() {
		return actionedBy;
	}
	public void setActionedBy(String actionedBy) {
		this.actionedBy = actionedBy;
	}
	public String getActionedTimestamp() {
		return actionedTimestamp;
	}
	public void setActionedTimestamp(String actionedTimestamp) {
		this.actionedTimestamp = actionedTimestamp;
	}
	public String getActionersUserAgentDetails() {
		return actionersUserAgentDetails;
	}
	public void setActionersUserAgentDetails(String actionersUserAgentDetails) {
		this.actionersUserAgentDetails = actionersUserAgentDetails;
	}
	@Override
	public String toString() {
		return "ActionedPaymentsDetails [id=" + id + ", flatNumber=" + flatNumber + ", date=" + date
				+ ", paymentMethod=" + paymentMethod + ", transactionId=" + transactionId + ", amount=" + amount
				+ ", rejectReason=" + rejectReason + ", action=" + action + ", actionedBy=" + actionedBy
				+ ", actionedTimestamp=" + actionedTimestamp + ", actionersUserAgentDetails="
				+ actionersUserAgentDetails + "]";
	}
}
