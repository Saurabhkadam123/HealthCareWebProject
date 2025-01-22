package com.java.prescription;

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
@FacesValidator("com.java.prescription.ValidateDate")

public class ValidateDate implements Validator {

	@Override
	public void validate(FacesContext cont, UIComponent comp, Object val) throws ValidatorException {

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Date beginDate = (Date) sessionMap.get("beginDate");
		Date endDate = (Date) val;
		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		if (beginDate.compareTo(endDate) > 0) {
			FacesMessage msg = new FacesMessage("END Date cannot be lesser than BEGIN Date !", uniqueColumn);
			cont.addMessage(comp.getClientId(cont), msg);
			throw new ValidatorException(msg);
		}

	}

}
