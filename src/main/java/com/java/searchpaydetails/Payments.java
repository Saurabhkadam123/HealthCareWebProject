package com.java.searchpaydetails;

import java.sql.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;


@ManagedBean(name="payments")
@SessionScoped
@Entity
@Table(name="finalpay")

public class Payments {
	
	@Id
	@Column(name="transactionId")
	private String transactionId;
	
	@Column(name="providerId")
	private String providerId;
	
	@Column(name="procedureId")
	private String ProcedureID;
	
	@Column(name="healthId")
	private String HealthID;
	
	@Column(name="claimId")
	private String claimId;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="PaidThrough")
	@Enumerated(EnumType.STRING)
	private PayEnum paidThrough;
	
	@Column(name="paymentsStatus")
	@Enumerated(EnumType.STRING)
	private PayStatus paymentsStatus;

	
	@Column(name="paymentAmount")
	private Double paymentAmount;
	
	@Column(name="paymentDate")
	private Date paymentDate;
	
	@Column(name="rejectReason")
	private String rejectReason;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProcedureID() {
		return ProcedureID;
	}

	public void setProcedureID(String procedureID) {
		ProcedureID = procedureID;
	}

	public String getHealthID() {
		return HealthID;
	}

	public void setHealthID(String healthID) {
		HealthID = healthID;
	}

	public String getClaimId() {
		return claimId;
	}

	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public PayEnum getPaidThrough() {
		return paidThrough;
	}

	public void setPaidThrough(PayEnum paidThrough) {
		this.paidThrough = paidThrough;
	}

	public PayStatus getPaymentsStatus() {
		return paymentsStatus;
	}

	public void setPaymentsStatus(PayStatus paymentsStatus) {
		this.paymentsStatus = paymentsStatus;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Override
	public String toString() {
		return "Payments [transactionId=" + transactionId + ", providerId=" + providerId + ", ProcedureID="
				+ ProcedureID + ", HealthID=" + HealthID + ", claimId=" + claimId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", paidThrough=" + paidThrough + ", paymentsStatus=" + paymentsStatus
				+ ", paymentAmount=" + paymentAmount + ", paymentDate=" + paymentDate + ", rejectReason=" + rejectReason
				+ "]";
	}
	
	
	
}
