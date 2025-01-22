package com.java.prescription;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.java.healthcare.sessionhelp.SessionHelper;
import com.java.project.Provider;
import com.java.viewapprove.Appointment;
import com.java.viewapprove.Status;

@ManagedBean(name = "procDao")
@SessionScoped
public class ProcedureDaoImpl implements ProcedureDAO {

	private String selectedRecipientId;
	private String selectedProblemType;
	private String selectedMedicineName;
	// private String selectedPharmaName;

	public void onRecipientIdChange(ValueChangeEvent vc) {
		this.selectedRecipientId = (String) vc.getNewValue();
		searchRecipientById(this.selectedRecipientId);
	}
	public boolean isFormShowable()
	{
		if(selectedRecipientId==null)
			return false;
		else
			return true;
	}
	public String getSelectedRecipientId() {
		return selectedRecipientId;
	}

	public void setSelectedRecipientId(String selectedRecipientId) {
		this.selectedRecipientId = selectedRecipientId;
	}

	public String getSelectedProblemType() {
		return selectedProblemType;
	}

	public void setSelectedProblemType(String selectedProblemType) {
		this.selectedProblemType = selectedProblemType;
	}

	public String getSelectedMedicineName() {
		return selectedMedicineName;
	}

	public void setSelectedMedicineName(String selectedMedicineName) {
		this.selectedMedicineName = selectedMedicineName;
	}

	public void onProblemTypeChange(ValueChangeEvent vc) {
		this.selectedProblemType = vc.getNewValue().toString();
		Procedure procedure = (Procedure) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("procedure");
		procedure.setProcedureCode(searchProCode(selectedProblemType));
		;
	}

	public String searchProCode(String st) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Procedurecode.class);
		cr.add(Restrictions.eq("procedureType", st));
		Procedurecode pr = (Procedurecode) cr.uniqueResult();
		session.close();

		return pr.getProcedureCode();
	}

	public void onMedicineNameChange(ValueChangeEvent vc) {
		this.selectedMedicineName = (String) vc.getNewValue();
		searchMedicine(selectedMedicineName);
		/*
		 * searchMedicineByMedPharmaName(selectedPharmaName,
		 * this.selectedMedicineName);
		 */ }

	/*
	 * public void onPharmaNameChange(ValueChangeEvent vc) {
	 * this.selectedPharmaName = vc.getNewValue().toString();
	 * searchMedicineByMedPharmaName(selectedPharmaName, selectedMedicineName);
	 * }
	 */

	public Recipient searchRecipientById(String recipientId) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Recipient.class);
		cr.add(Restrictions.eq("healthId", recipientId));
		Recipient recipient = (Recipient) cr.uniqueResult();
		session.close();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("recipientInfo", recipient);
		return recipient;
	}

	public Medicine searchMedicine(String medicineName) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Medicine.class);
		cr.add(Restrictions.eq("medicineName", medicineName));
		// cr.add(Restrictions.eq("pharmaName", pharmaName));
		Medicine medicine = (Medicine) cr.uniqueResult();
		session.close();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("medicineInfo", medicine);
		return medicine;
	}

	public List<String> loadMedicineName() {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Medicine.class);
		cr.setProjection(Projections.distinct(Projections.property("medicineName")));

		if (selectedProblemType != null) {
			if (selectedProblemType.equals("SURGERY"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.SURGERY));
			else if (selectedProblemType.equals("BODYPAIN"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.BODYPAIN));
			else if (selectedProblemType.equals("EYEPAIN"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.EYEPAIN));
			else if (selectedProblemType.equals("ROOTCANAL"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.ROOTCANAL));
			else if (selectedProblemType.equals("FEVER"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.FEVER));
			else if (selectedProblemType.equals("HEADACHE"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.HEADACHE));
			else if (selectedProblemType.equals("ASHTMA"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.ASHTMA));
			else if (selectedProblemType.equals("STOMACHE"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.STOMACHE));
			else if (selectedProblemType.equals("JOINTPAIN"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.JOINTPAIN));
			else if (selectedProblemType.equals("ULCER"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.ULCER));
			else if (selectedProblemType.equals("COUGH"))
				cr.add(Restrictions.eq("procedureType", ProcedureType.COUGH));
		}
		List<String> medicines = cr.list();
		return medicines;
	}

	public List<String> loadRecipientIds() {
		Map<String, Object> sessMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Provider loggedProvider = (Provider) sessMap.get("providerInfo");

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Appointment.class);
		cr.setProjection(Projections.property("healthId"));
		cr.add(Restrictions.eq("status", Status.Confirm));
		cr.add(Restrictions.eq("appDate", new Date()));
		cr.add(Restrictions.eq("pId", loggedProvider.getProviderId()));
		return cr.list();
	}

	/*
	 * public List<String> loadPharmaciesByMedName() { SessionFactory sf =
	 * SessionHelper.getConnection(); Session session = sf.openSession();
	 * Criteria cr = session.createCriteria(Medicine.class);
	 * cr.setProjection(Projections.property("pharmaName"));
	 * cr.add(Restrictions.eq("medicineName", selectedMedicineName)); return
	 * cr.list(); }
	 */

	public String generateProcedureId() {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Procedure.class);
		cr.setProjection(Projections.max("procedureID"));
		String procedureIdStr = (String) cr.uniqueResult();
		session.close();
		if (procedureIdStr == null)
			return "PR001";
		int procId = Integer.parseInt(procedureIdStr.substring(2));

		return String.format("PR%03d", ++procId);
	}

	@Override
	public String addProcedure(Procedure procedure) {
		Map<String, Object> sessMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Provider loggedProvider = (Provider) sessMap.get("providerInfo");

		Medicine medicineInfo = (Medicine) sessMap.get("medicineInfo");
		Recipient recipientInfo = (Recipient) sessMap.get("recipientInfo");
		procedure.setItemId(medicineInfo.getMedicineID());
		procedure.setProcedureID(generateProcedureId());
		procedure.setProviderId(loggedProvider.getProviderId());
		procedure.setProviderType(loggedProvider.getSpecialization());
		procedure.setRecipientfirstName(recipientInfo.getRecipientfirstName());
		procedure.setRecipientlastName(recipientInfo.getRecipientlastName());
		procedure.setRecipientDob(recipientInfo.getRecipientDob());
		procedure.setRecipientmobileNo(recipientInfo.getRecipientmobileNo());

		// procedure.setMedicinePrice(medicineInfo.getMedicinePrice());
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction trans = session.beginTransaction();
		session.save(procedure);
		trans.commit();
		session.close();
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"You succesfully added the prescription ",
				null);

		FacesContext context = FacesContext.getCurrentInstance();
		Flash flash = context.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		context.addMessage(null, message);

		return "ShowProcedure.xhtml?faces-redirect=true";
	}

	public List<Procedure> showProcedureDetails() {
		Map<String, Object> sessMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Provider loggedProvider = (Provider) sessMap.get("providerInfo");

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Procedure.class);
		cr.add(Restrictions.eq("providerId", loggedProvider.getProviderId()));
		List<Procedure> procList = cr.list();
		return procList;
	}

	/*
	 * String currentStatusValue;
	 * 
	 * 
	 * public void onStatusChange(ValueChangeEvent vce) { currentStatusValue =
	 * vce.getNewValue().toString(); if (currentStatusValue.equals("INACTIVE"))
	 * { System.out.println("Faces msg reading"); FacesMessage message = new
	 * FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "For inactive cant process further", null); FacesContext context =
	 * FacesContext.getCurrentInstance(); context.addMessage(null, message);
	 * System.out.println("Faces msg readed success"); } }
	 */
}
