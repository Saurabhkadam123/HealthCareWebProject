package com.java.healthcare.entity;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@ManagedBean(name = "avlDetails")
@SessionScoped
public class AvailableDetails {

	@Id
	private String availableId;
	private String providerId;
	private Date availableDate;
	private Date startTime;
	private Date endTime;

	public String getAvailableId() {
		return availableId;
	}

	public void setAvailableId(String availableId) {
		this.availableId = availableId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
