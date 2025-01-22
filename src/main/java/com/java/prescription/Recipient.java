package com.java.prescription;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean(name="recipient")
@SessionScoped
@Entity
@Table(name="signuprecipient")


public class Recipient {
	
	@Id
	@Column(name="healthId")
	private String healthId;
	
	@Column(name="city")
	private String city;
	
	@Column(name="dob")
	private Date recipientDob;
	
	@Column(name="firstName")
	private String recipientfirstName;
	
	@Column(name="gender")
	private String gender;

	@Column(name="lastName")
	private String recipientlastName;	
	
	@Column(name="mobileNo")
	private String recipientmobileNo;

	@Column(name="state")
	private String state;
	
	@Column(name="zipcode")
	private String zipcode;
	
	@Column(name="loginId")
	private int loginId;
	
	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getRecipientDob() {
		return recipientDob;
	}

	public void setRecipientDob(Date recipientDob) {
		this.recipientDob = recipientDob;
	}

	public String getRecipientfirstName() {
		return recipientfirstName;
	}

	public void setRecipientfirstName(String recipientfirstName) {
		this.recipientfirstName = recipientfirstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRecipientlastName() {
		return recipientlastName;
	}

	public void setRecipientlastName(String recipientlastName) {
		this.recipientlastName = recipientlastName;
	}

	public String getRecipientmobileNo() {
		return recipientmobileNo;
	}

	public void setRecipientmobileNo(String recipientmobileNo) {
		this.recipientmobileNo = recipientmobileNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	
}
