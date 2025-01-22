package com.java.project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
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
@FacesValidator("com.java.project.PhoneNumberCheck")
public class PhoneNumberCheck implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes().get("currentEntity");
	    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
		boolean isValid = false;
		try {
	        
	    	 String phone = (String) value;
	    	 char ch = phone.charAt(0);
	    	 
	    	 int foundCount = 0;
	    	 
	    	 for(int i = 0; i<phone.length(); i++)
	    	 {
	    		 if(!Character.isDigit(phone.charAt(i)))
    				{
	    			 	foundCount++;	
    				}
	    	 }
	    	 
	    	 if(foundCount > 0)
	    	 {
	    		 FacesMessage msg = new FacesMessage("Letters, special characters or white spaces are not allowed", uniqueColumn);
		            context.addMessage(comp.getClientId(context), msg);
		            throw new ValidatorException(msg);
	    	 }
	    	 
	    	 
	    	 //TO CHECK IF THE NUMBER STARTS FROM ZERO.
	    	if(ch == '0')
	    	{
	    		FacesMessage msg = new FacesMessage("Phone number cannot start with zero", uniqueColumn);
	            context.addMessage(comp.getClientId(context), msg);
	            throw new ValidatorException(msg);
	    		
	    	}
	    	
	        
	    } catch (NoResultException ex) {
	        isValid = true; // good! no result means unique validation was OK!
	    }

		
	}
	

}
