package com.java.claim;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.java.prescription.ProcedureType;

@ManagedBean(name = "ClaimViewfield")
@ViewScoped
@Entity
@Table(name = "ClaimViewfield")
public class ClaimViewfield implements Serializable {
	@Id
	@Column(name = "HealthId")
	private String healthId;

	@Column(name = "MedicineTotalCost")
	private double medicineTotalCost;

	@Column(name = "ProcedureId")
	private String procedureId;

	@Column(name = "PId")
	private String pId;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Enumerated(EnumType.STRING)
	@Column(name = "ProcedureType")
	private ProcedureType ProcedureType;

	@Column(name = "ProcedureBeginDate")
	private Date procedureBeginDate;

	@Column(name = "ProcedureEndDate")
	private Date procedureEndDate;

	@Column(name = "InsuranceId")
	private String insuranceId;

	public String getHealthId() {
		return healthId;
	}

	public String getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public ProcedureType getProcedureType() {
		return ProcedureType;
	}

	public void setProcedureType(ProcedureType procedureType) {
		ProcedureType = procedureType;
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

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public double getMedicineTotalCost() {
		return medicineTotalCost;
	}

	public void setMedicineTotalCost(double medicineTotalCost) {
		this.medicineTotalCost = medicineTotalCost;
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

	@Override
	public String toString() {
		return "Viewfield [healthId=" + healthId + ", medicineTotalCost=" + medicineTotalCost + ", procedureId="
				+ procedureId + ", pId=" + pId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", ProcedureType=" + ProcedureType + ", procedureBeginDate=" + procedureBeginDate
				+ ", procedureEndDate=" + procedureEndDate + ", insuranceId=" + insuranceId + "]";
	}
}
