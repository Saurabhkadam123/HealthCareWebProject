package com.java.project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("CityValidation")
public class CityValidation implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
	    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
	    
	    String city = (String) value;
	    boolean notValid = false;
	    
	    for(int i = 0; i<city.length(); i++)
	    {
	    	if(Character.isDigit(city.charAt(i)) 
	    			|| Character.isWhitespace(city.charAt(i)) 
	    					|| (!Character.isDigit(city.charAt(i)) && !Character.isLetter(city.charAt(i))
	    							&& !Character.isWhitespace(city.charAt(i))))
	    	{
	    		notValid = true;
	    	}
	    }
	    
	    if(notValid)
	    {
	    	FacesMessage msg = new FacesMessage("Cannot contain any special characters, digits and white spaces", uniqueColumn);
            context.addMessage(comp.getClientId(context), msg);
            throw new ValidatorException(msg);
	    }
	}

}
