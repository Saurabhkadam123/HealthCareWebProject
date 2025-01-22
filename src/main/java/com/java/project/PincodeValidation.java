package com.java.project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("PincodeValidation")
public class PincodeValidation implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
	    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
	    
	    String pincode = (String) value;
	    boolean hasCharacters = false;
	    
	    
	    for(int i = 0; i<pincode.length(); i++)
	    {
	    	if(!Character.isDigit(pincode.charAt(i)) )
	    	{
	    		hasCharacters = true;
	    	}
	    }
	    
	    if(hasCharacters)
	    {
	    	FacesMessage msg = new FacesMessage("Cannot contain letters, special characters or white spaces", uniqueColumn);
			context.addMessage(comp.getClientId(context), msg);
			throw new ValidatorException(msg);
	    }
	}

}
