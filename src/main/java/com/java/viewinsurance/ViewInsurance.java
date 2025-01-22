package com.java.viewinsurance;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@ManagedBean(name="viewInsurance")
@Entity
@Table(name="ViewInsurance")
public class ViewInsurance {
	@Id
	@Column(name="I_PLAN_ID")
	private String planId;
	
	@Column(name="healthId")
	private String healthId;
	@Column(name="I_PLAN_Name")
	private String planName;
	
	@Column(name="I_PLAN_TYPE")
	private String planType;
	
	@Column(name="coverageapplicable")
	private String coverageapplicable;
	@Column(name="status_1")
	private String status;
	@Column(name="policybuydate")
	private Date policyBuyDate;
	@Column(name="Expirydate")
	private Date expiryDate;
	@Column(name="premiumapplicable")
	private double premiumapplicable;
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	public String getCoverageapplicable() {
		return coverageapplicable;
	}
	public void setCoverageapplicable(String coverageapplicable) {
		this.coverageapplicable = coverageapplicable;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPolicyBuyDate() {
		return policyBuyDate;
	}
	public void setPolicyBuyDate(Date policyBuyDate) {
		this.policyBuyDate = policyBuyDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public double getPremiumapplicable() {
		return premiumapplicable;
	}
	public void setPremiumapplicable(double premiumapplicable) {
		this.premiumapplicable = premiumapplicable;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getHealthId() {
		return healthId;
	}
	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}
	
}
