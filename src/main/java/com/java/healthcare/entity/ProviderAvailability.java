package com.java.healthcare.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.event.SelectEvent;

@Entity
@Table
@ManagedBean(name = "provAvl")
@SessionScoped
public class ProviderAvailability {
	@Id
	private String availabilityId;
	private String providerId;
	@Transient
	private String availabilityType;
	private Date availableDate;
	@Transient
	private List<Date> multiAvailableDate;
	@Transient
	private List<Date> rangeAvailableDate;
	private String startTime;
	private String endTime;
	@Enumerated(EnumType.STRING)
	@Column(insertable = false)
	private AvailableStatus currentStatus;
	@Column(insertable = false)
	private int appointmentDuration;
	@Transient
	private List<String> loadTimings = Arrays.asList("00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30",
			"04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
			"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
			"16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
			"22:00", "22:30", "23:30");

	public Date todaysDate() {
		return new Date();
	}

	public void onAvlDateSelect(SelectEvent<Date> event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Available Date Selected",
				format.format(event.getObject())));
	}

	public void onMultiAvlDateSelect(SelectEvent<List<Date>> event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		for (Date date : event.getObject()) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Available Date Selected", format.format(date)));
		}
	}

	public AvailabilityType[] loadAvailabilityTypes() {
		return AvailabilityType.values();
	}

	public String getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(String availabilityId) {
		this.availabilityId = availabilityId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getAvailabilityType() {
		return availabilityType;
	}

	public void setAvailabilityType(String availabilityType) {
		this.availabilityType = availabilityType;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public List<Date> getMultiAvailableDate() {
		return multiAvailableDate;
	}

	public void setMultiAvailableDate(List<Date> multiAvailableDate) {
		this.multiAvailableDate = multiAvailableDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Date> getRangeAvailableDate() {
		return rangeAvailableDate;
	}

	public void setRangeAvailableDate(List<Date> rangeAvailableDate) {
		this.rangeAvailableDate = rangeAvailableDate;
	}

	public AvailableStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(AvailableStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<String> getLoadTimings() {

		return loadTimings;
	}

	public void setLoadTimings(List<String> loadTimings) {
		this.loadTimings = loadTimings;
	}

	public int getAppointmentDuration() {
		return appointmentDuration;
	}

	public void setAppointmentDuration(int appointmentDuration) {
		this.appointmentDuration = appointmentDuration;
	}

	@Override
	public String toString() {
		return "ProviderAvailability [availabilityId=" + availabilityId + ", providerId=" + providerId
				+ ", availabilityType=" + availabilityType + ", availableDate=" + availableDate
				+ ", multiAvailableDate=" + multiAvailableDate + ", rangeAvailableDate=" + rangeAvailableDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", currentStatus=" + currentStatus
				+ ", loadTimings=" + loadTimings + "]";
	}

}
