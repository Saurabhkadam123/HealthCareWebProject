package com.java.searchclaims;


import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.java.claim.ClaimIdValidator")
public class ClaimIdValidator implements Validator {

	 private static final Pattern PATTERN1 = Pattern.compile("[A-Z]{1}[0-9]{3}");
	 private static final Pattern PATTERN2 = Pattern.compile("[A-Z]{2}[0-9]{4}");

	    @Override
	    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    	
	    	System.out.println("claim id validator got called...");
	        String input =  (String) value;
	        System.out.println("input is :"+input);
	        int lg=input.length();
	       
	     
	      if(lg==4 &&input != null && !PATTERN1.matcher(input).matches()) {
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter Valid  Alphanumeric value ", null);
	            throw new ValidatorException(message);
	        }
	        else if(lg==6 &&input != null && !PATTERN2.matcher(input).matches()){
	        	
	        	 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter Valid Alphanumeric value ", null);
		            throw new ValidatorException(message);
	        	
	        }
	        
	    }

}
