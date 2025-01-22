package com.java.prescription;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean(name = "procedure")
@SessionScoped
@Entity
@Table(name = "hmsprocedure")
public class Procedure {

	@Id
	@Column(name = "procedureID")
	private String procedureID;

	@Column(name = "pId")
	private String providerId;

	@Column(name = "Specialization")
	private String providerType;

	@Column(name = "healthId")
	private String healthId;

	@Column(name = "itemId")
	private int itemId;

	@Column(name = "firstName")
	private String recipientfirstName;

	@Column(name = "lastName")
	private String recipientlastName;

	@Column(name = "dob")
	private Date recipientDob;

	@Column(name = "mobileNo")
	private String recipientmobileNo;

	@Column(name = "Item_Name")
	private String medicineName;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "procedureType")
	@Enumerated(EnumType.STRING)
	private ProcedureType procedureType;

	@Column(name = "procedureCode")
	private String procedureCode;

	@Column(name = "procedureDescription")
	private String procedureDescription;

	@Column(name = "procedureBeginDate")
	private Date procedureBeginDate;

	@Column(name = "procedureEndDate")
	private Date procedureEndDate;

	public String getProcedureID() {
		return procedureID;
	}

	public void setProcedureID(String procedureID) {
		this.procedureID = procedureID;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getRecipientfirstName() {
		return recipientfirstName;
	}

	public void setRecipientfirstName(String recipientfirstName) {
		this.recipientfirstName = recipientfirstName;
	}

	public String getRecipientlastName() {
		return recipientlastName;
	}

	public void setRecipientlastName(String recipientlastName) {
		this.recipientlastName = recipientlastName;
	}

	public Date getRecipientDob() {
		return recipientDob;
	}

	public void setRecipientDob(Date recipientDob) {
		this.recipientDob = recipientDob;
	}

	public String getRecipientmobileNo() {
		return recipientmobileNo;
	}

	public void setRecipientmobileNo(String recipientmobileNo) {
		this.recipientmobileNo = recipientmobileNo;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ProcedureType getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(ProcedureType procedureType) {
		this.procedureType = procedureType;
	}

	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	public String getProcedureDescription() {
		return procedureDescription;
	}

	public void setProcedureDescription(String procedureDescription) {
		this.procedureDescription = procedureDescription;
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

	public ProcedureType[] loadProcedureType() {
		return ProcedureType.values();
	}

}
