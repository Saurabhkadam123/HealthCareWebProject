package com.java.viewinsurance;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@ManagedBean(name="insurance")
@SessionScoped
@Entity
@Table(name="insurance")
public class Insurance {

	/*@Id
	@GenericGenerator(name = "custom_generator", strategy = "com.java.hms.PIdGenerator")
	@GeneratedValue(generator = "custom_generator")*/
	@Id
	@Column(name = "planId")
	private String planId;
	
	private String cId;
	
	@Column(name = "premiumId")
	private String premiumId;
	
	@Column(name = "planName")	
	private String planName;
	
	@Column(name = "planType")
	private String planType;
	
	
	
	@Column(name = "premium")
	private double premium;
	
	@Column(name = "coverage")
	private double coverage;
	
	@Column(name = "launchDate")
	private Date launchDate;
	
	@Column(name = "expiryDate")
	private Date expiryDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "keyBenifits")
	private String keyBenifits;
	
	
	
	
	
	/*@ManyToMany(mappedBy = "insurance")  
	private List<Recipient> recipient;


	public List<Recipient> getRecipient() {
		return recipient;
	}

	public void setRecipient(List<Recipient> recipient) {
		this.recipient = recipient;
	}*/

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}



	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getCoverage() {
		return coverage;
	}

	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	
	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getKeyBenifits() {
		return keyBenifits;
	}

	public void setKeyBenifits(String keyBenifits) {
		this.keyBenifits = keyBenifits;
	}

	public String getPremiumId() {
		return premiumId;
	}

	public void setPremiumId(String premiumId) {
		this.premiumId = premiumId;
	}	
	
}