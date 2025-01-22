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

@ManagedBean(name="medicine")
@SessionScoped
@Entity
@Table(name="stock")

public class Medicine {
	
	@Id
	@Column(name="itemId")
	private int medicineID;
	
	@Column(name="pharmaId")
	private int pharmaId;
	
	@Column(name="pharmaName")
	private String pharmaName;
	
	@Column(name="itemName")
	private String medicineName;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="price")
	private Double medicinePrice;
	
	@Column(name="mfgDate")
	private Date mfgDate;
	
	@Column(name="expiryDate")
	private Date expiryDate;
	
	@Column(name="vendor")
	private String vendor;
	
	@Column(name = "procedureType")
	@Enumerated(EnumType.STRING)
	private ProcedureType procedureType;

	public int getMedicineID() {
		return medicineID;
	}

	public void setMedicineID(int medicineID) {
		this.medicineID = medicineID;
	}

	public int getPharmaId() {
		return pharmaId;
	}

	public void setPharmaId(int pharmaId) {
		this.pharmaId = pharmaId;
	}

	public String getPharmaName() {
		return pharmaName;
	}

	public void setPharmaName(String pharmaName) {
		this.pharmaName = pharmaName;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getMedicinePrice() {
		return medicinePrice;
	}

	public void setMedicinePrice(Double medicinePrice) {
		this.medicinePrice = medicinePrice;
	}

	public Date getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(Date mfgDate) {
		this.mfgDate = mfgDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public ProcedureType getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(ProcedureType procedureType) {
		this.procedureType = procedureType;
	}

	
		

}
