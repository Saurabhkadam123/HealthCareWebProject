package com.java.project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("ExperienceValidation")
public class ExperienceValidation implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
	    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
	    
	    String experience = (String) value;
	    boolean valid = true;
	    
	    for(int i = 0; i<experience.length(); i++)
	    {
	    	if(!Character.isDigit(experience.charAt(i)))
	    	{
	    		valid = false;
	    	}
	    }
	    
	    if(!valid)
	    {
	    	FacesMessage msg = new FacesMessage("Invalid experience, should be +ve numericial value", uniqueColumn);
            context.addMessage(comp.getClientId(context), msg);
            throw new ValidatorException(msg);
	    }
	    
	    else if(valid)
	    {
	    	int ex = Integer.parseInt(experience);
	    	if(ex > 50)
	    	{
	    		FacesMessage msg = new FacesMessage("Experience exceeds 50 years", uniqueColumn);
	            context.addMessage(comp.getClientId(context), msg);
	            throw new ValidatorException(msg);
	    	}
	    	
	    }
	    
	    
	}

}
