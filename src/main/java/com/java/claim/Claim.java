package com.java.claim;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean(name = "claim")
@ViewScoped
@Entity
@Table(name = "ClaimForm")
public class Claim {

	@Id
	@Column(name = "ClaimId")
	private String claimId;

	@Column(name = "ProcedureId")
	private String procedureId;

	@Column(name = "PId")
	private String pId;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "HealthId")
	private String healthId;

	@Column(name = "Justification")
	private String justification;

	@Column(name = "InsuranceId")
	private String insuranceId;

	@Column(name = "medicineTotalCost")
	private double medicineTotalCost;

	@Column(name = "ProcedureCharges")
	private double procedureCharges;

	@Column(name = "TotalAmount")
	private double totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "Status", insertable = true)
	private Status status = Status.PENDING;

	@Column(name = "Date")
	private Date date;

	@Column(name = "ProcedureBeginDate")
	private Date procedureBeginDate;

	@Column(name = "ProcedureEndDate")
	private Date procedureEndDate;

	@Column(name = "ProcedureType")
	private String procedureType;

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}

	public String getClaimId() {
		return claimId;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public double getProcedureCharges() {
		return procedureCharges;
	}

	public void setProcedureCharges(double procedureCharges) {
		this.procedureCharges = procedureCharges;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getProcedureBeginDate() {
		return procedureBeginDate;
	}

	public void setProcedureBeginDate(Date procedureBeginDate) {
		this.procedureBeginDate = procedureBeginDate;
	}

	public Date getProcedureEndDate() {
		return procedureEndDate;
	}

	public void setProcedureEndDate(Date procedureEndDate) {
		this.procedureEndDate = procedureEndDate;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public double getMedicineTotalCost() {
		return medicineTotalCost;
	}

	public void setMedicineTotalCost(double medicineTotalCost) {
		this.medicineTotalCost = medicineTotalCost;
	}

	@Override
	public String toString() {
		return "Claim [claimId=" + claimId + ", procedureId=" + procedureId + ", pId=" + pId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", healthId=" + healthId + ", justification=" + justification
				+ ", insuranceId=" + insuranceId + ", medicineTotalCost=" + medicineTotalCost + ", procedureCharges="
				+ procedureCharges + ", totalAmount=" + totalAmount + ", status=" + status + ", date=" + date
				+ ", procedureBeginDate=" + procedureBeginDate + ", procedureEndDate=" + procedureEndDate
				+ ", procedureType=" + procedureType + "]";
	}
	

}
