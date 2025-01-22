package com.java.viewapprove;


import java.sql.Time;
import java.sql.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean(name="appointment")
@SessionScoped
@Entity
@Table(name="appointment")
public class Appointment {

	@Id
	@Column(name="A_Id")
	private String aId;
	
	@Column(name="healthId")
	private String healthId;
	
	@Column(name="P_Id")
	private String pId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="Status")
	private Status status; 
	
	@Column(name="Adate")
	Date appDate;
	
	@Column(name="startTime") 
	Time startTime; 
	
	@Column(name="endTime")
    Time endTime;

	public String getaId() {
		return aId;
	}

	public void setaId(String aId) {
		this.aId = aId;
	}

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Appointment [aId=" + aId + ", healthId=" + healthId + ", pId=" + pId + ", status=" + status
				+ ", appDate=" + appDate + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}


	
	

}