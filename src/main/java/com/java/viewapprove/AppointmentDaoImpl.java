package com.java.viewapprove;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

@ManagedBean(name = "oDao")
@SessionScoped
public class AppointmentDaoImpl implements AppointmentDao {

	public List<Appointment> getListAppointment() {
		return listAppointment;
	}

	public void setListAppointment(List<Appointment> listAppointment) {
		this.listAppointment = listAppointment;
	}

	private Date filter;
	List<Appointment> aList;

	public List<Appointment> getaList() {
		return aList;
	}

	public void setaList(List<Appointment> aList) {
		this.aList = aList;
	}

	public Date getFilter() {
		return filter;
	}

	public void setFilter(Date filter) {
		this.filter = filter;
	}

	private List<Appointment> listAppointment;

	public List<Appointment> getListAppoitment() {
		return listAppointment;
	}

	public void setListAppoitment(List<Appointment> listAppoitment) {
		this.listAppointment = listAppoitment;
	}

	public AppointmentDaoImpl() {
		listAppointment = null;
	}

	@Override
	public List<Appointment> viewAppointmentDao() {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();

		Criteria cr = session.createCriteria(Appointment.class);
		List<Appointment> applist = cr.list();
		if (applist.size() == 0) {
			return null;
		}
		return applist;
	}

	public void changeMe(ValueChangeEvent e) throws ParseException {
		Date dt = (Date) e.getNewValue();
		System.out.println(dt);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Appointment.class);
		cr.add(Restrictions.eq("appDate", dt));
		List<Appointment> applist = cr.list();
		System.out.println(applist.size());
		listAppointment = applist;
		System.out.println(listAppointment.size());
		System.out.println(listAppointment);

	}

	public String approvedStatusDao(Appointment appointment) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		appointment.setStatus(Status.Confirm);
		Transaction t = session.beginTransaction();
		session.update(appointment);
		t.commit();

		return "ViewAppointment.xhtml?faces-redirect=true";

	}

	public String rejectStatusDao(Appointment appointment) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		appointment.setStatus(Status.Cancel);
		Transaction t = session.beginTransaction();
		session.update(appointment);
		t.commit();

		return "ViewAppointment.xhtml?faces-redirect=true";

	}

	public List<Appointment> showAll(String str) {
		List<Appointment> aList = new ArrayList<Appointment>();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();

		Criteria cr = session.createCriteria(Appointment.class);

		if (str.trim().length() == 0) {
			aList = null;
			return aList;
		} else {
			if (str.equals("appdate")) {
				cr.add(Restrictions.eq("appdate", str));
				aList = cr.list();
			}
			return aList;
		}
	}

	public List<Appointment> display(String str) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Appointment.class);
		cr.add(Restrictions.eq("appDate", filter));
		cr.add(Restrictions.eq("status", Status.valueOf(str)));
		List<Appointment> aList = cr.list();
		System.out.println("Specific List Size -->" + cr.list().size());
		listAppointment = aList;
		System.out.println(listAppointment);
		return listAppointment;

	}

	public boolean check(String status, Date appDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			if (status == "Confirm" || status == "Cancel"
					|| (status == "Pending" && appDate.compareTo(sdf.parse(sdf.format(new Date()))) < 0)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean check1(String status, Date appDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			if (status == "Cancel"|| status=="Pending" || appDate.compareTo(sdf.parse(sdf.format(new Date()))) < 0){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	

}
