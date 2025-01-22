package com.java.healthcare.entity;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;
import com.java.healthcare.sessionhelp.SessionHelper;

@RequestScoped
@FacesValidator("com.java.healthcare.entity.DateValidation")
public class DateValidation implements Validator, Serializable {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		
		boolean isValid=true;
		
		ProviderAvailability provAvailable = (ProviderAvailability)FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("provAvl");
		
		System.out.println(""+provAvailable.getAvailabilityType());
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		Criteria cr = session.createCriteria(ProviderAvailability.class);
		cr.add(Restrictions.eq("availableDate", value));
		cr.add(Restrictions.eq("providerId", "P001"));
		cr.setProjection(Projections.rowCount());
		Long count = (Long) cr.uniqueResult();
		
		if(count==1){
//			FacesMessage msg = new FacesMessage("Password doesn't exist !..", uniqueColumn);
//			context.addMessage(comp.getClientId(context), msg);
//			throw new ValidatorException(msg);
		}
		
//		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes().get("currentEntity");
//		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
//		boolean isValid = false;
//		try {
//		SessionFactory sf = SessionHelper.getConnection();
//		Session session = sf.openSession();
//		Transaction t = session.beginTransaction();
//		Criteria cr = session.createCriteria(ProviderAvailability.class);
//		cr.add(Restrictions.eq("availableDate", value));
//		cr.setProjection(Projections.rowCount());
//		Long count = (Long) cr.uniqueResult();
//			if(count == 0){
//				FacesMessage msg = new FacesMessage("Password doesn't exist !..", uniqueColumn);
//				context.addMessage(comp.getClientId(context), msg);
//				throw new ValidatorException(msg);
//			}
//		}catch (NoResultException ex){
////			isValid=true;
//		}         
	}
}
