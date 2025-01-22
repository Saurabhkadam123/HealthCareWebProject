package com.java.project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("com.java.HospitalValidation")
public class HospitalValidation implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		  String uniqueColumn = (String) component.getAttributes().get("uniqueColumn");
		  
		  String name = (String) value;
		  boolean valid = true;
		  
		  for(int i = 0; i<name.length(); i++)
		  {
			  if(Character.isDigit(name.charAt(i)) || (!Character.isDigit(name.charAt(i)) && !Character.isLetter(name.charAt(i))
						&& !Character.isWhitespace(name.charAt(i)))   )
			  {
				  valid = false;
			  }
		  }
		  
		  if(!valid)
		  {
			  FacesMessage msg = new FacesMessage("Cannot contain special characters or digits", uniqueColumn);
				context.addMessage(component.getClientId(context), msg);
				throw new ValidatorException(msg);
		  }

	}

}
