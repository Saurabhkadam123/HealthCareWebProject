package com.java.prescription;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean(name="prCode")
@SessionScoped
@Entity
@Table(name="procedurecode")

public class Procedurecode {
	
	@Id
	@Column(name="proId")
	private int proId;
	
	@Column(name="procedureCode")
	private String procedureCode;
	
	@Column(name="procedureType")
	private String procedureType;

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public String getProcedureCode() {
		return procedureCode;
	}

	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}
	
	
	
	
	

}
