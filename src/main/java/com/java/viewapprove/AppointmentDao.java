package com.java.viewapprove;

import java.util.List;


public interface AppointmentDao {

	List <Appointment>viewAppointmentDao();
	public List<Appointment> display(String status);
	
}
