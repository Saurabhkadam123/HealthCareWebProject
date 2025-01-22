package com.java.claim;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.java.healthcare.sessionhelp.SessionHelper;

@ManagedBean(name = "i")
@ViewScoped
public class Impl {

	String procedureId;
	ClaimViewfield vObject;
	String inId;

	public String getInId() {
		return inId;
	}

	public void setInId(String inId) {
		this.inId = inId;
	}

	public String getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}

	List<String> insuranceIdList;

	public List<String> getInsuranceIdList() {
		return insuranceIdList;
	}

	public void setInsuranceIdList(List<String> insuranceIdList) {
		this.insuranceIdList = insuranceIdList;
	}

	public ClaimViewfield getvObject() {
		return vObject;
	}

	public void setvObject(ClaimViewfield vObject) {
		this.vObject = vObject;
	}

	public List<String> getPidFromView() {
		List<String> pidList = new ArrayList<>();

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();

		Criteria cr = session.createCriteria(ClaimViewfield.class);
		cr.setProjection(Projections.distinct(Projections.property("procedureId")));
		List<String> allPidList = cr.list();

		Criteria cr2 = session.createCriteria(Claim.class);
		cr2.setProjection(Projections.property("procedureId"));
		List<String> claimPidList = cr2.list();

		for (String pid : allPidList) {
			if (!claimPidList.contains(pid)) {
				pidList.add(pid);
			}
		}

		session.close();
		return pidList;
	}

	public void searchViewField() {

		String pid = this.procedureId;

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(ClaimViewfield.class);
		cr.add(Restrictions.eq("procedureId", pid));
		ClaimViewfield viewfield = (ClaimViewfield) cr.uniqueResult();
		System.out.println(viewfield);
		vObject = viewfield;
		insuranceIdList = getInsuranceIds(vObject.getProcedureId());
		inId = insuranceIdList.get(0);

	}

	public List<String> getInsuranceIds(String pid) {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(ClaimViewfield.class);
		cr.add(Restrictions.eq("procedureId", pid))
				.setProjection(Projections.distinct(Projections.property("insuranceId")));

		return cr.list();

	}
}
