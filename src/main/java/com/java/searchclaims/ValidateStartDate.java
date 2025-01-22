package com.java.searchclaims;

import java.util.Map;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("com.java.claim.ValidateStartDate")
public class ValidateStartDate implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		Map<String,Object> sessMap=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessMap.put("startDateEntered", value);
		System.out.println("Object succes saved to map");
	}

}
