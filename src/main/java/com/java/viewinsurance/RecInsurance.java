package com.java.viewinsurance;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@ManagedBean(name = "rec_insurance")
@ViewScoped
@Entity
@Table(name = "rec_insurance")
public class RecInsurance {

	@Column(name = "healthId")
	private String healthId;

	@Column(name = "I_PLAN_ID")
	private String planId;

	@Column(name = "I_COMPANY_ID")
	private String cId;

	@Id
	@GenericGenerator(name = "custom_generator", strategy = "com.java.viewinsurance.Eidgenerator")
	@GeneratedValue(generator = "custom_generator")
	@Column(name = "InsuranceID")
	private String InsuranceID;

	@Column(name = "policybuydate")
	private Date policybuydate;

	@Column(name = "Expirydate")
	private Date expirydate;

	@Column(name = "status_1")
	private String status;

	@Column(name = "provider_Id")
	private String providerId;

	@Column(name = "Role")
	private String role;

	@Column(name = "premiumapplicable")
	private double premiumapplicable;

	@Column(name = "coverageapplicable")
	private double coverageapplicable;

	public double getPremiumapplicable() {
		return premiumapplicable;
	}

	public void setPremiumapplicable(double premiumapplicable) {
		this.premiumapplicable = premiumapplicable;
	}

	public double getCoverageapplicable() {
		return coverageapplicable;
	}

	public void setCoverageapplicable(double coverageapplicable) {
		this.coverageapplicable = coverageapplicable;
	}

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;

	}

	public String getInsuranceID() {
		return InsuranceID;
	}

	public void setInsuranceID(String insuranceID) {
		InsuranceID = insuranceID;
	}

	public Date getPolicybuydate() {
		return policybuydate;
	}

	public void setPolicybuydate(Date policybuydate) {
		this.policybuydate = policybuydate;
	}

	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}
}
