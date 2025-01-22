package com.java.project;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

import com.mysql.cj.Messages;

@RequestScoped
@FacesValidator("com.java.project.uniqueColumnValidator")
public class UniqueColumnValidator implements Validator, Serializable {

    @PersistenceContext
    protected EntityManager em;

    /**
     * generic unique constraint validator for AbstractBaseEntity entities<br />
     * requires the following additional attributes on the form element ("<f:attribute>"):<br />
     * - "currentEntity" the entity instance (used for getting the class and guid)<br />
     * - "uniqueColumn" the column where the new value will be checked for uniqueness
     */
    @Override
    public void validate(final FacesContext context, final UIComponent comp, final Object newValue) throws ValidatorException {

    AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes().get("currentEntity");
    String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

    boolean isValid = false;
    try {
        
    	SessionFactory sf = SessionHelper.getConnection();
    	Session session = sf.openSession();
    	
    	Transaction t = session.beginTransaction();
    	Criteria cr = session.createCriteria(Provider.class);
    	cr.add(Restrictions.eq("username", newValue));
    	cr.setProjection(Projections.rowCount());
    	Long count = (Long) cr.uniqueResult();
    	if(count == 1)
    	{
    		FacesMessage msg = new FacesMessage("Username already taken", uniqueColumn);
            context.addMessage(comp.getClientId(context), msg);
            throw new ValidatorException(msg);
    		
    	}
    	
        
    } catch (NoResultException ex) {
        isValid = true; // good! no result means unique validation was OK!
    }

    
    }

	
}
