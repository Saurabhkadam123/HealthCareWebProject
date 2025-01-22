package com.java.prescription;

import java.util.Map;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@RequestScoped
@FacesValidator("com.java.prescription.SaveBeginDate")
public class SaveBeginDate implements Validator {

	@Override
	public void validate(FacesContext cont, UIComponent comp, Object val) throws ValidatorException {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("beginDate", val);
		System.out.println("thank u visit again");
	}

}
