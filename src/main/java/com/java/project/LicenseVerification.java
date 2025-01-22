package com.java.project;

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

@RequestScoped
@FacesValidator("com.java.project.LicenseVerification")
public class LicenseVerification implements Validator, Serializable{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes().get("currentEntity");
	    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
		boolean isValid = false;
		try {
	        
	    	SessionFactory sf = SessionHelper.getConnection();
	    	Session session = sf.openSession();
	    	
	    	Transaction t = session.beginTransaction();
	    	Criteria cr = session.createCriteria(License.class);
	    	cr.add(Restrictions.eq("licenseId", value));
	    	cr.setProjection(Projections.rowCount());
	    	Long count = (Long) cr.uniqueResult();
	    	if(count == 0)
	    	{
	    		FacesMessage msg = new FacesMessage("Invalid license id", uniqueColumn);
	            context.addMessage(comp.getClientId(context), msg);
	            throw new ValidatorException(msg);
	    		
	    	}
	    	
	        
	    } catch (NoResultException ex) {
	        isValid = true; // good! no result means unique validation was OK!
	    }

		
	}

}
