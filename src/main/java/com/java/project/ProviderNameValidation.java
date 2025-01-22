package com.java.project;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.swing.text.AttributeSet.CharacterAttribute;

@RequestScoped
@FacesValidator("com.java.project.ProviderNameValidation")
public class ProviderNameValidation implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		String fname = (String) value;
		int count = 0;

		if (fname.length() == 1) {
			if (Character.isLetter(fname.charAt(0))) 
			{
				FacesMessage msg = new FacesMessage("Cannot be in short form", uniqueColumn);
				context.addMessage(comp.getClientId(context), msg);
				throw new ValidatorException(msg);
			}
		}

		for (int i = 0; i < fname.length(); i++) {
			
			if(Character.isDigit(fname.charAt(i)) 
					|| Character.isWhitespace(fname.charAt(i)) 
					|| (!Character.isDigit(fname.charAt(i)) && !Character.isLetter(fname.charAt(i))
							&& !Character.isWhitespace(fname.charAt(i))) )
			{
				count++;
			}
			
			 

			 
		}

		if (count > 0) {
			FacesMessage msg = new FacesMessage("Cannot contain digits, special character and white spaces", uniqueColumn);
			context.addMessage(comp.getClientId(context), msg);
			throw new ValidatorException(msg);
		}

		 
	}

}
