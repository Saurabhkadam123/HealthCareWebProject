package com.java.project;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.java.loginProvider.Login;

@Entity
@Table(name = "Provider")
@ManagedBean(name = "provider")
@ViewScoped
public class Provider {

	@Id
	@Column(name = "pId")
	private String providerId;
	@Column(name = "pDob")
	private Date providerDob;
	@Column(name = "firstName")
	private String fristName;
	@Column(name = "middleName")
	private String middleName;
	@Column(name = "lastName")
	private String lastName;
	@Enumerated(EnumType.STRING)
	@Column(name = "pGender")
	private Gender providerGender;
	@Column(name = "specialization")
	private String specialization;
	@Column(name = "experience")
	private String experience;

	@Column(name = "licenseId")
	private String licenseId;

	@Column(name = "aadharNo")
	private String aadharNo;
	/*
	 * @Enumerated(EnumType.STRING)
	 * 
	 * @Column(name = "language") private Language language;
	 */
	@Column(name = "State")
	private String state;
	@Column(name = "city")
	private String city;
	@Column(name = "StreetName")
	private String streetName;
	@Column(name = "landmark")
	private String landMark;
	@Column(name = "Address1")
	private String address1;
	@Column(name = "Address2")
	private String address2;

	@Column(name = "MobileNo")
	private String phoneNo;
	@Column(name = "pEmailId")
	private String providerEmailId;
	@Enumerated(EnumType.STRING)
	@Column(name = "question")
	private Questions questions;

	@Enumerated(EnumType.STRING)
	@Column(name = "question2")
	private Questions2 question2;
	@Column(name = "answer")
	private String answer;

	@Column(name = "answer2")
	private String answer2;

	@Column(name = "username")
	private String username;

	@Column(name = "education")
	private String education;
	@Column(name = "council")
	private String council;
	@Column(name = "category")
	private String category;
	@Column(name = "speciality")
	private String speciality;
	@Column(name = "pincode")
	private String pincode;

	@Column(name = "Hospital")
	private String hospital;

	// my code

	@Enumerated(EnumType.STRING)
	@Column(name = "pStatus")
	private Status status;

	@Column(name = "comment")
	private String comment;

	@Column(name = "registerDate", insertable = false)
	private Date registerDate;

	@Column(name = "expiryDate", insertable = false)
	private Date expiryDate;

	@Column(name = "suspendedDate")
	private Date suspendedDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fkId")
	private Login login;

	@Column(name = "fkId", insertable = false, updatable = false)
	private int fkId;

	@Column(name = "npiId")
	private String npiId;

	public String getNpiId() {
		return npiId;
	}

	public void setNpiId(String npid) {
		this.npiId = npid;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Date getProviderDob() {
		return providerDob;
	}

	public void setProviderDob(Date providerDob) {
		this.providerDob = providerDob;
	}

	public String getFristName() {
		return fristName;
	}

	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getProviderGender() {
		return providerGender;
	}

	public void setProviderGender(Gender providerGender) {
		this.providerGender = providerGender;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getProviderEmailId() {
		return providerEmailId;
	}

	public void setProviderEmailId(String providerEmailId) {
		this.providerEmailId = providerEmailId;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	

	public Questions2 getQuestion2() {
		return question2;
	}

	public void setQuestion2(Questions2 question2) {
		this.question2 = question2;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCouncil() {
		return council;
	}

	public void setCouncil(String council) {
		this.council = council;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getSuspendedDate() {
		return suspendedDate;
	}

	public void setSuspendedDate(Date suspendedDate) {
		this.suspendedDate = suspendedDate;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public int getFkId() {
		return fkId;
	}

	public void setFkId(int fkId) {
		this.fkId = fkId;
	}

	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", providerDob=" + providerDob + ", fristName=" + fristName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", providerGender=" + providerGender
				+ ", specialization=" + specialization + ", experience=" + experience + ", licenseId=" + licenseId
				+ ", aadharNo=" + aadharNo + ", state=" + state + ", city=" + city + ", streetName=" + streetName
				+ ", landMark=" + landMark + ", address1=" + address1 + ", address2=" + address2 + ", phoneNo="
				+ phoneNo + ", providerEmailId=" + providerEmailId + ", questions=" + questions + ", question2="
				+ question2 + ", answer=" + answer + ", answer2=" + answer2 + ", username=" + username + ", education="
				+ education + ", council=" + council + ", category=" + category + ", speciality=" + speciality
				+ ", pincode=" + pincode + ", hospital=" + hospital + ", status=" + status + ", comment=" + comment
				+ ", registerDate=" + registerDate + ", expiryDate=" + expiryDate + ", suspendedDate=" + suspendedDate
				+ ", login=" + login + ", fkId=" + fkId + "]";
	}

}
