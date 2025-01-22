package com.java.project;

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
@FacesValidator("com.java.project.AadharVerification")
public class AadharVerification implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes().get("currentEntity");
	    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
		boolean isValid = false;
		try {
			String aadhar = (String) value;
			char ch = aadhar.charAt(0);
	        
	    	SessionFactory sf = SessionHelper.getConnection();
	    	Session session = sf.openSession();
	    	
	    	Transaction t = session.beginTransaction();
	    	Criteria cr = session.createCriteria(Provider.class);
	    	cr.add(Restrictions.eq("aadharNo", value));
	    	cr.setProjection(Projections.rowCount());
	    	Long count = (Long) cr.uniqueResult();
	    	if(count == 1)
	    	{
	    		FacesMessage msg = new FacesMessage("An account already registered with this aadhaar", uniqueColumn);
	            context.addMessage(comp.getClientId(context), msg);
	            throw new ValidatorException(msg);
	    		
	    	}
	    	
	    	else if(aadhar.length()<12)
	    	{
	    		if(ch == '0' || ch == '1')
	    		{
	    			FacesMessage msg = new FacesMessage("Cannot start with 0 or 1", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    		}
	    		
	    		else if(check(aadhar))
	    		{
	    			FacesMessage msg = new FacesMessage("Cannot contain any special characters, letters and white spaces", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    		}
	    		
	    		else{
	    			 
	    			FacesMessage msg = new FacesMessage("Less than 12 digits", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    		}
	    		
	    	}
	    	
	    	else if(aadhar.length()>12)
	    	{
	    		if(ch == '0' || ch == '1')
	    		{
	    			FacesMessage msg = new FacesMessage("Cannot start with 0 or 1", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    		}
	    		
	    		else if(check(aadhar))
	    		{
	    			FacesMessage msg = new FacesMessage("Cannot contain any special characters, letters and white spaces", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    		}
	    		
	    		else{
	    			FacesMessage msg = new FacesMessage("Greater than 12 digits", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    		}
	    		
	    	}
	    	
	    	else{
	    		
	    		if( ch == '0' || ch == '1')
		    	{
		    		FacesMessage msg = new FacesMessage("Cannot start with 0 or 1", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
		    	}
	    		
	    		else if(check(aadhar))
	    		{
	    			FacesMessage msg = new FacesMessage("Cannot contain any special characters, letters and white spaces", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    		}
	    		
	    		
	    		
	    	}
	    	
	    	
	    	 
	    	
	    	
	    	
	        
	    } catch (NoResultException ex) {
	        isValid = true; // good! no result means unique validation was OK!
	    }
	}
	
	public boolean check(String value)
	{
		String aadhaarTocheck = value;
    	int foundCount = 0;
    	
    	for(int i = 0; i<aadhaarTocheck.length(); i++)
    	{
    		if(!Character.isDigit(aadhaarTocheck.charAt(i))
                   )
    		{
    			foundCount++;
    		}
    	}
    	
    	if(foundCount > 0)
    	{
    		return true;
    	}
    	
    	else{
    		return false;
    	}
	}

}
