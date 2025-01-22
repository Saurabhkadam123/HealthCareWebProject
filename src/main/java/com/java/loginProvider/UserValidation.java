package com.java.loginProvider;

import java.io.Serializable;

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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.java.loginProvider.Login;
import com.java.loginProvider.SessionHelper;

@SuppressWarnings("serial")
@RequestScoped
@FacesValidator("com.java.loginProvider.UserValidation")
public class UserValidation implements Validator, Serializable{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		
		comp.getAttributes().get("currentEntity");
	    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
	    
		try {
	        
	    	SessionFactory sf = SessionHelper.getConnection();
	    	Session session = sf.openSession();
	    	
	    	session.beginTransaction();
	    	Criteria cr = session.createCriteria(Login.class);
	    	cr.add(Restrictions.eq("username", value));
	    	cr.setProjection(Projections.rowCount());
	    	Long count = (Long) cr.uniqueResult();
	    	if(count == 0)
	    	{
	    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Username...!", uniqueColumn);
	            context.addMessage(comp.getClientId(context), msg);
	            throw new ValidatorException(msg);
	    	}
	    	
	        
	    } catch (NoResultException ex) {
	    }

		
	}

}
