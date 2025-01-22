package com.java.project;

 

import java.util.Calendar;
import java.util.Date;

 

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

 

 

@FacesValidator("dobValidator")
public class DOBValidator implements Validator {
  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	  String uniqueColumn = (String) component.getAttributes().get("uniqueColumn");
    Date dob = (Date) value;

    if (dob == null) {
      FacesMessage msg = new FacesMessage("Date of birth validation failed.", "DOB is required.");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ValidatorException(msg);
    }
    
    

 

    // Create a Calendar object with today's date and clear the time
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);

 

    // Create a Calendar object with the DOB and clear the time
    Calendar dobCalendar = Calendar.getInstance();
    dobCalendar.setTime(dob);
    dobCalendar.set(Calendar.HOUR_OF_DAY, 0);
    dobCalendar.set(Calendar.MINUTE, 0);
    dobCalendar.set(Calendar.SECOND, 0);
    dobCalendar.set(Calendar.MILLISECOND, 0);

 

    // Check if DOB is in the future
    if (dobCalendar.after(today)) {
    	FacesMessage msg = new FacesMessage("DOB cannot be a future date", uniqueColumn);
        context.addMessage(component.getClientId(context), msg);
        throw new ValidatorException(msg);
    }
    
    //CHECK IF PROVIDER IS MINOR OR ADULT
    else if(!dobCalendar.after(today))
    {
    	 
		Date currDate = new Date();
		
		int diff = currDate.getYear() - dob.getYear();
		
		if(diff < 18)
		{
			
			FacesMessage msg = new FacesMessage("Must be above 18 years", uniqueColumn);
            context.addMessage(component.getClientId(context), msg);
            throw new ValidatorException(msg);
		}
    }
  }
}