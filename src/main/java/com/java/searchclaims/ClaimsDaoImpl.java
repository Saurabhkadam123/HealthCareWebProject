package com.java.searchclaims;

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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

import com.java.claim.Claim;
import com.java.claim.Status;
import com.java.healthcare.sessionhelp.SessionHelper;
import com.java.project.Provider;

@ManagedBean(name = "claimDao")
@SessionScoped
public class ClaimsDaoImpl {

	private String searchType = "";
	private String searchValue = "";
	private String selectedStatus = "PENDING";
	private Date searchedStartDate;
	private Date searchedEndDate;

	List<Claim> claimsList;
	List<Claim> filteredList;

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

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public Date getSearchedStartDate() {
		return searchedStartDate;
	}

	public void setSearchedStartDate(Date searchedStartDate) {
		this.searchedStartDate = searchedStartDate;
	}

	public Date getSearchedEndDate() {
		return searchedEndDate;
	}

	public void setSearchedEndDate(Date searchedEndDate) {
		this.searchedEndDate = searchedEndDate;
	}

	public List<Claim> getClaimsList() {
		return claimsList;
	}

	public void setClaimsList(List<Claim> claimsList) {
		this.claimsList = claimsList;
	}

	public List<Claim> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Claim> filteredList) {
		this.filteredList = filteredList;
	}

	public void onSearchTypeChange(ValueChangeEvent vce) {
		this.searchType = (String) vce.getNewValue();
		System.out.println("Setting searchType :" + searchType);
		if (searchType.equals("Status")) {
			System.out.println("Selected Status set to'' ");
			selectedStatus = "";
		} else if (searchType.equals("Claim Id") || searchType.equals("Health Id")) {
			System.out.println("SearchVal set to'' ");
			searchValue = "";
		}
	}

	public void onChangeSearchValue(ValueChangeEvent vc) {
		this.searchValue = (String) vc.getNewValue();
		System.out.println("Setting searchVal :" + searchValue);
	}

	public void onStatusChange(ValueChangeEvent vc) {
		this.selectedStatus = (String) vc.getNewValue();
	}

	public Status[] loadStatusValues() {
		return Status.values();
	}

	public void onSearchStartDateChange(ValueChangeEvent vc) {
		this.searchedStartDate = (Date) vc.getNewValue();
		System.out.println("Setting start Date :" + (Date) vc.getNewValue());
	}

	public void onStartDateSelect(SelectEvent<Date> event) {
		this.searchedStartDate = event.getObject();
		System.out.println("Start Date selected and set sccess :" + searchedStartDate);
	}

	public void onEndDateSelect(SelectEvent<Date> event) {
		this.searchedEndDate = event.getObject();
		System.out.println("End Date selected and set sccess :" + searchedEndDate);
	}

	public void onSearchEndDateChange(ValueChangeEvent vc) {
		this.searchedEndDate = (Date) vc.getNewValue();
		System.out.println("Setting end Date :" + (Date) vc.getNewValue());

	}

	public List<Claim> showClaimDetailsNew() {
		System.out.println("in show method");

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		Provider loggedProvider = (Provider) sessionMap.get("providerInfo");

		claimsList = new ArrayList<Claim>();

		if (!searchType.equals("")) {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria cr = session.createCriteria(Claim.class);
			cr.add(Restrictions.eq("pId", loggedProvider.getProviderId()));
			if (searchType.equals("Claim Id")) {
				System.out.println("showMethod:claimID");
				if (!searchValue.equals("")) {

					cr.add(Restrictions.like("claimId", searchValue, MatchMode.ANYWHERE));
					claimsList = cr.list();
					filteredList = claimsList;
					System.out.println("claimId restr adeded list loiaded");
				}
			} else if (searchType.equals("Health Id")) {

				if (!searchValue.equals("")) {

					cr.add(Restrictions.like("healthId", searchValue, MatchMode.ANYWHERE));
					claimsList = cr.list();
					filteredList = claimsList;

				}
			} else if (searchType.equals("Status")) {
				System.out.println("------------------in status ");
				if (!selectedStatus.equals("")) {

					if (selectedStatus.equals("DENY"))
						cr.add(Restrictions.like("status", Status.DENY));
					else if (selectedStatus.equals("APPROVED"))
						cr.add(Restrictions.like("status", Status.APPROVED));
					else
						cr.add(Restrictions.like("status", Status.PENDING));
					claimsList = cr.list();
					filteredList = claimsList;
					System.out.println("List loaded");
				}
			} else if (searchType.equals("Date")) {
				System.out.println("in date searchtype");
				System.out.println(searchedStartDate);
				System.out.println(searchedEndDate);
				if (searchedStartDate != null && searchedEndDate != null) {
					System.out.println("date searchtype if block success");
					System.out.println(searchedStartDate);
					System.out.println(searchedEndDate);
					cr.add(Restrictions.between("date", searchedStartDate, searchedEndDate));
					claimsList = cr.list();
					filteredList = claimsList;
				}
			}

		}
		System.out.println("----------------------------------------------------------------------------");
		System.out.println(claimsList);
		sessionMap.put("mappedClaimList", claimsList);
		List<Claim> claimList = claimsList;
		return claimList;
	}

	public Date todaysDate() {
		return new Date();
	}

}
