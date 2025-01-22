package com.java.healthcare.entity;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.java.project.Provider;
import com.java.project.SessionHelper;

@RequestScoped
@FacesValidator("com.java.healthcare.entity.ValidateAvailabilityType")
public class ValidateAvailabilityType implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {

		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		String selectedAvailabilityType = (String) value;
		
		if (!selectedAvailabilityType.equals("SINGLE_DAY")&&!selectedAvailabilityType.equals("RANDOM_DAYS")&&!selectedAvailabilityType.equals("RECURING")) {

			FacesMessage msg = new FacesMessage("Please select availability type first !", uniqueColumn);
			context.addMessage(comp.getClientId(context), msg);
			throw new ValidatorException(msg);

		}

	}

}
