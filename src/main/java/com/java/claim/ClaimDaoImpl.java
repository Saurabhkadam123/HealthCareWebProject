package com.java.claim;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.java.healthcare.sessionhelp.SessionHelper;
import com.java.viewinsurance.RecInsurance;

@ManagedBean(name = "cDao")
@ViewScoped
public class ClaimDaoImpl implements ClaimDao {

	@Override
	public List<Claim> showClaimDetailsDao() {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Claim.class);
		List<Claim> ClaimList = cr.list();
		return ClaimList;

	} 
	public String updateClaim(String claimId, String insuranceId, double procedureCharge) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Claim details = searchClaimById(claimId);
		if (details.getProcedureCharges() < 0) {
			String message = "please give charge in positive value";
			FacesContext.getCurrentInstance().addMessage("form:forNumber",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			return null;
		}

		details.setInsuranceId(insuranceId);
		details.setStatus(Status.PENDING);
		Transaction t = session.beginTransaction();
		System.out.println(details.getProcedureCharges());
		double totalAmount = details.getTotalAmount() - details.getProcedureCharges();
		details.setTotalAmount(totalAmount + procedureCharge);
		details.setProcedureCharges(procedureCharge);
		session.update(details);
		t.commit();
		return "UpdatedText.xhtml?faces-redirect=true";
	}


	public Claim searchClaimById(String claimId) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Claim.class);
		cr.add(Restrictions.eq("claimId", claimId));
		Claim foundClaim = (Claim) cr.uniqueResult();
		return foundClaim;
	}

	@Override
	public String searchClaim(String claimId) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Claim.class);
		cr.add(Restrictions.eq("claimId", claimId));
		Claim record = (Claim) cr.uniqueResult();
		sessionMap.put("claimInfo", record);
		return "UpdateClaim.xhtml?faces-redirect=true";
	}

	@Override
	public String generateClaimId() {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Claim.class);
		List<Claim> cList = cr.list();

		if (cList.size() == 0) {
			return "C001";
		}
		cr.setProjection(Projections.max("claimId"));
		String str = (String) cr.uniqueResult();
		String id = str.substring(1);
		int temp = Integer.parseInt(id);
		temp++;
		return String.format("C%03d", temp);

	}

	// (viewTable)..............................................................................

	private double procedureCharge;

	public double getProcedureCharge() {
		return procedureCharge;
	}

	public void setProcedureCharge(double procedureCharge) {
		this.procedureCharge = procedureCharge;
	}

	@Override
	public String addClaimViewfield(Claim claim, ClaimViewfield viewfield) {
		if (claim != null && viewfield != null) {
			if (procedureCharge < 0) {
				String message = "please give charge in positive value";
				FacesContext.getCurrentInstance().addMessage("form:forNumber",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
				return null;
			}
			double total = (procedureCharge + viewfield.getMedicineTotalCost());
			claim.setClaimId(generateClaimId());
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria cr = session.createCriteria(Claim.class);
			Transaction t = session.beginTransaction();

			claim.setHealthId(viewfield.getHealthId());
			claim.setpId(viewfield.getpId());
			claim.setFirstName(viewfield.getFirstName());
			claim.setLastName(viewfield.getLastName());
			claim.setProcedureBeginDate(viewfield.getProcedureBeginDate());
			claim.setProcedureEndDate(viewfield.getProcedureEndDate());
			claim.setMedicineTotalCost(viewfield.getMedicineTotalCost());
			claim.setProcedureCharges(procedureCharge);
			claim.setTotalAmount(total);
			claim.setProcedureId(viewfield.getProcedureId());
			claim.setInsuranceId(viewfield.getInsuranceId());
			claim.setProcedureType(viewfield.getProcedureType().toString());
			claim.setDate(new Date());
			session.save(claim);
			t.commit();

			return "SumittedText.xhtml?message=addedsuccessfully";
		}
		return "";
	}

	public List<String> searchViewField(String healthId) {
		SessionFactory sessionFactory = SessionHelper.getConnection();
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(RecInsurance.class);
		criteria.add(Restrictions.eq("healthId", healthId));
		criteria.setProjection(Projections.property("InsuranceID"));
		List<String> list = criteria.list();

		return list;

	}

}
