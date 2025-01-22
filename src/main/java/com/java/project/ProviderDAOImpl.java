package com.java.project;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import com.java.loginProvider.Login;


@ManagedBean(name = "pDao")
@ViewScoped
public class ProviderDAOImpl implements ProviderDAO {

	/*
	 * public String verify(String username) { SessionFactory sf =
	 * SessionHelper.getConnection(); Session session = sf.openSession();
	 * Criteria cr = session.createCriteria(Provider.class);
	 * cr.add(Restrictions.eq("username", username)); Long count =
	 * (Long)cr.uniqueResult(); if(count == 0) { return username; }
	 * 
	 * 
	 * return null; }
	 */

	// AUTO GENERATES PROVIDER'S ID
	private String generateProviderID() {
		SessionFactory sfactory = SessionHelper.getConnection();
		Session session = sfactory.openSession();
		Criteria criteria = session.createCriteria(Provider.class).setProjection(Projections.max("providerId"));
		String str = (String) criteria.uniqueResult();
		if (str == null) {
			return "P001";

		}
		String order = str.substring(1);
		int pId = Integer.parseInt(order);
		pId++;
		return String.format("P%03d", pId);
	}

	public String generateNPIID() {
		SessionFactory sfactory = SessionHelper.getConnection();
		Session session = sfactory.openSession();
		Criteria criteria = session.createCriteria(Login.class).setProjection(Projections.max("npiId"));
		String str = (String) criteria.uniqueResult();
		System.out.println("----------------------------npi str-----" + str);
		if (str == null) {
			return "NPI0000001";

		}
		String order = str.substring(3);
		System.out.println("order----------------" + order);
		long npiId = Long.parseLong(order);
		npiId++;
		System.out.println("npiId ----------" + npiId);
		System.out.println("NPI Print ----------------------" + String.format("NPI%07d", npiId));

		return String.format("NPI%07d", npiId);
	}

	@Override
	public String addProvider(Provider provider) {

		String ans = EntryptPassword.getCode(provider.getAnswer());
		String ans2 = EntryptPassword.getCode(provider.getAnswer2());
		String pid = generateProviderID();
		provider.setProviderId(pid);
		provider.setAnswer(ans);
		provider.setAnswer2(ans2);
		// admin code
		
		String user=provider.getUsername();
		provider.setStatus(Status.PENDING);
		/*System.out.println("Usrename--------------------------------"+user);
		provider.getLogin().setUsername(user);
		System.out.println("-------------------------------------------");
		*/
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		Login p1 = new Login();
	//	p1.setNpiId(generateNPIID());

		provider.setLogin(p1);

		Transaction t = session.beginTransaction();
		session.save(provider);
		t.commit();
		return "HomePage.xhtml?faces-redirect=true";
	}

	/*
	 * @Override public String addProvider(Provider provider) {
	 * 
	 * String ans = EntryptPassword.getCode(provider.getAnswer()); String pid =
	 * generateProviderID(); provider.setProviderId(pid);
	 * provider.setAnswer(ans); provider.set SessionFactory sf =
	 * SessionHelper.getConnection(); Session session = sf.openSession();
	 * Criteria cr = session.createCriteria(Provider.class);
	 * 
	 * Transaction t = session.beginTransaction(); session.save(provider);
	 * t.commit(); return "Show.xhtml?faces-redirect=true"; }
	 */

	@Override
	public List<Provider> showProvider() {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		List<Provider> pList = cr.list();
		return pList;
	}

}
