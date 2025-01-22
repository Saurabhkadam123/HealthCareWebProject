package com.java.searchclaims;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("com.java.claim.ValidateEndDate")
public class ValidateEndDate implements Validator {

	@Override
	public void validate(FacesContext cont, UIComponent comp, Object val) throws ValidatorException {

		Map<String, Object>      sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Date beginDate = (Date) sessionMap.get("startDateEntered");
		Date endDate = (Date) val;
		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		if (beginDate.compareTo(endDate) > 0) {
			FacesMessage msg = new FacesMessage("END date cannot be below than START Date !", uniqueColumn);
			cont.addMessage(comp.getClientId(cont), msg);
			System.out.println("Going to throw val exp");
			throw new ValidatorException(msg);
		}
		
	}

}
 