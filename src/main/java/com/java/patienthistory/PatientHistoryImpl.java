package com.java.patienthistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.java.healthcare.sessionhelp.SessionHelper;
import com.java.prescription.Procedure;
import com.java.project.Provider;


@ManagedBean(name="searchHisDao")
@SessionScoped
public class PatientHistoryImpl {

	private String errorMessage;

	private List<Procedure> filteredList;

	public List<Procedure> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Procedure> filteredList) {
		this.filteredList = filteredList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isStringOnlyAlphabet(String str) // regex expression for only
													// contains alphabet
	{
		return ((!str.equals("")) && (str != null) && (str.matches("^[a-zA-Z]*$")));
	}

	public boolean isStringOnlyDigit(String str) // regex expression for only
													// contains digit
	{
		return ((!str.equals("")) && (str != null) && (str.matches("\\d+")));
	}

	public boolean maxTenDigit(String str) // regex expression for contains max
											// ten digit
	{
		return ((!str.equals("")) && (str != null) && (str.matches("[1-9]{1}[0-9]{9}")));
	}

	public List<Procedure> searchHistory(String str, String str1) {

		Map<String, Object> sesMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Provider loggedProvider= (Provider) sesMap.get("providerInfo");
		List<Procedure> pList = new ArrayList<Procedure>();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Procedure.class);
		cr.add(Restrictions.eqOrIsNull("providerId", loggedProvider.getProviderId()));
		if (str.trim().length() == 0) {
			errorMessage = "Please Enter Value....";
			pList = null;
			filteredList = null;
			return pList;
		}

		else {

			if (str1.equals("healthId")) {

				errorMessage = "";
				cr.add(Restrictions.like("healthId", str, MatchMode.ANYWHERE));
				pList = cr.list();
				filteredList = pList;

			}

			if (str1.equals("Name")) {

				boolean flag = isStringOnlyAlphabet(str);

				if (flag == false) {
					errorMessage = "Please Enter Only Characters....";
					filteredList = null;
					return null;
				}

				else {
					
					errorMessage="";
					Criterion firstName = Restrictions.like("recipientfirstName", str , MatchMode.ANYWHERE);
					Criterion lastName =Restrictions.like("recipientlastName", str, MatchMode.ANYWHERE);
					LogicalExpression orExp = Restrictions.or(firstName, lastName);
					cr.add(orExp);
					pList = cr.list();
					filteredList=pList;
					
				}
			}

			if (str1.equals("recipientmobileNo")) {

				boolean flag = isStringOnlyDigit(str);
				boolean flag1 = maxTenDigit(str);

				if (flag == false) {
					errorMessage = "Please Enter Only Digit....";
					filteredList = null;
					return null;
				}

				if (flag1 == false) {
					errorMessage = "Please Enter 10 Digit Mobile No....";
					filteredList = null;
					return null;
				}

				else {
					errorMessage = "";
					cr.add(Restrictions.like("recipientmobileNo", str, MatchMode.ANYWHERE));
					pList = cr.list();
					filteredList = pList;
				}
			}
			filteredList = pList;
			return pList;
		}
	}
}
