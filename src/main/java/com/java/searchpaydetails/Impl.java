package com.java.searchpaydetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.java.healthcare.sessionhelp.SessionHelper;


@ManagedBean(name = "DaoImpl")
@SessionScoped
public class Impl {

	public static String searchType;
	private String searchValue;
	private String selectedPayMode;
	private String selectedStatus;

	List<Payments> payList;
	List<Payments> filteredList;

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSelectedPayMode() {
		return selectedPayMode;
	}

	public void setSelectedPayMode(String selectedPayMode) {
		this.selectedPayMode = selectedPayMode;
	}

	public List<Payments> getPayList() {
		return payList;
	}

	public void setPayList(List<Payments> payList) {
		this.payList = payList;
	}

	public List<Payments> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Payments> filteredList) {
		this.filteredList = filteredList;
	}

	public void onSearchTypeChange(ValueChangeEvent vce) {
		this.searchType = (String) vce.getNewValue();
		System.out.println("Setting searchType :" + searchType);
	}

	public void onChangeSearchValue(ValueChangeEvent vc) {
		this.searchValue = (String) vc.getNewValue();
		System.out.println("Setting searchVal :" + searchValue);
	}

	public void onPaidChange(ValueChangeEvent vc) {
		this.selectedPayMode = (String) vc.getNewValue();
	
	}
	
	public void onStatusChange(ValueChangeEvent vc) {
		
		this.selectedStatus = (String) vc.getNewValue();
	}

	public PayEnum[] loadPaidValues() {
		return PayEnum.values();
	}
	
	public PayStatus[] loadStatusValues() {
		return PayStatus.values();
	}

	public String showClaimDetails() {
		System.out.println("in show method");

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		payList = new ArrayList<Payments>();

		if (!searchType.equals("")) {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria cr = session.createCriteria(Payments.class);

			if (searchType.equals("ProcedureID")) {

				if (!searchValue.equals("")) {
					cr.add(Restrictions.like("ProcedureID", searchValue, MatchMode.ANYWHERE));
					payList = cr.list();
					filteredList = payList;
				}
			} else if (searchType.equals("HealthID")) {

				if (!searchValue.equals("")) {

					cr.add(Restrictions.like("HealthID", searchValue, MatchMode.ANYWHERE));
					payList = cr.list();
					filteredList = payList;
				}
			} else if (searchType.equals("paidThrough") ) {
				System.out.println("------------------in PayMode ");
				if (selectedPayMode != "") {

					if (selectedPayMode.equals("PhonePe"))
						cr.add(Restrictions.like("paidThrough", PayEnum.PhonePe));
					else if (selectedPayMode.equals("GPay"))
						cr.add(Restrictions.like("paidThrough", PayEnum.GPay));
					else if (selectedPayMode.equals("Paytm"))
						cr.add(Restrictions.like("paidThrough", PayEnum.Paytm));
					

					payList = cr.list();
					filteredList = payList;
					System.out.println("List loaded");
				}
			}else if (searchType.equals("paymentsStatus")) {
				System.out.println("------------------in status ");
				if (selectedStatus != "") {
					System.out.println("----Status Loaded----");
					if (selectedStatus.equals("PAID"))
						cr.add(Restrictions.like("paymentsStatus", PayStatus.PAID));
					else if (selectedStatus.equals("REJECT"))
						cr.add(Restrictions.like("paymentsStatus", PayStatus.REJECT));			
				
					
					payList = cr.list();
					filteredList = payList;
					System.out.println("List loaded");
				}
			}

		}
		System.out.println("----------------------------------------------------------------------------");
		System.out.println(payList);
		sessionMap.put("mappedClaimList", payList);
		return "SearchPage.xhtml?facas-redirect=true";
	}

	public String addPay(Payments payments) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Payments.class);
		Transaction t = session.beginTransaction();
		session.save(payments);
		t.commit();

		return "showClaim.xhtml?faces-redirect=true";
	}

	public List<Payments> ShowPayments() {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Payments.class);
		List<Payments> list = cr.list();

		return list;

	}


}
