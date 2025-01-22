package com.java.viewinsurance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.java.healthcare.sessionhelp.SessionHelper;
import com.java.project.Provider;
import com.java.viewapprove.Appointment;

@ManagedBean(name = "iDao")
@SessionScoped
public class InsuranceDAOImpl implements InsuranceDAO {

	public List<Appointment> searchCompleteHealthId() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		Provider loggedProvider = (Provider) sessionMap.get("providerInfo");

		List<Appointment> list = new ArrayList<>();
		try {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();

			session = sf.openSession();
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.add(Restrictions.eq("status", Status.Confirm));
			criteria.add(Restrictions.eq("pId", loggedProvider.getProviderId()));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

	public Appointment searchHealthId(String hVal) {
		List<Appointment> list = new ArrayList<>();
		try {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.add(Restrictions.eq("healthId", hVal));
			criteria.add(Restrictions.eq("status", Status.Confirm));
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Insurance searchByInsuranceName(String planName) {
		Insurance insurance = new Insurance();
		try {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria cr = session.createCriteria(Insurance.class);
			cr.add(Restrictions.eq("planName", planName));
			insurance = (Insurance) cr.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return insurance;
	}

	public String searchRecipientInfo(String healthId) {
		try {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria criteria = null;
			List<ViewInsurance> insuranceFound = null;
			if (healthId != null) {
				/* String hId = appointment.getHealthId(); */
				// System.out.println(hId);
				criteria = session.createCriteria(ViewInsurance.class);
				criteria.add(Restrictions.eq("healthId", healthId));
				insuranceFound = criteria.list();
				Map<String, Object> sessMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
				sessMap.put("insuranceListInfo", insuranceFound);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return "ViewInsuranceRec.xhtml?faces-redirect=true";
	}

	public List showInsurance(String hVal) {

		List jList=new ArrayList();
		try {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria criteria = null;
			List<RecInsurance> pId = null;
			criteria = session.createCriteria(RecInsurance.class);// Select All
			criteria.add(Restrictions.eq("healthId", hVal)); // where class
			pId = criteria.list();
			List<ViewInsurance> insurances = null;
			jList = new ArrayList<>();
			for (RecInsurance id : pId) {
				String planId = id.getPlanId();
				criteria = session.createCriteria(ViewInsurance.class);
				criteria.add(Restrictions.eq("planId", planId));
				criteria.add(Restrictions.eq("healthId", hVal));
				insurances = criteria.list();
				jList.add(insurances);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return jList;

	}

}
