package com.java.searchpaydetails;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

import com.java.healthcare.sessionhelp.SessionHelper;

@ViewScoped
@FacesValidator("payment.details.Validate")
public class Validate implements Validator, Serializable {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes()
				.get("currentEntity");
		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		boolean isValid = false;
		try {
			String Svalue = Impl.searchType;
			String input = (String) value;

			Long count = 0l;
			System.out.println("searchtype" + Svalue);

			org.hibernate.SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Transaction t = session.beginTransaction();

			if (Svalue.equalsIgnoreCase("ProcedureID")) {

				if (input.length() == 0) {
					FacesMessage msg = new FacesMessage("Please Enter procedure ID", uniqueColumn);
					context.addMessage(comp.getClientId(context), msg);
					throw new ValidatorException(msg);
				}

				else if (input.length() > 0) {
					if (input.length() >= 2) {

						if (!(input.charAt(0) == 'P' && input.charAt(1) == 'R')) {

							FacesMessage msg = new FacesMessage("Procedure Id should start with PR", uniqueColumn);
							context.addMessage(comp.getClientId(context), msg);
							throw new ValidatorException(msg);
						}

						else {

							boolean valid = true;
							for (int i = 2; i < input.length(); i++) {
								if (!Character.isDigit(input.charAt(i))) {
									valid = false;
								}
							}

							if (!valid) {
								FacesMessage msg = new FacesMessage("ID must be in Format 'PR001' ", uniqueColumn);
								context.addMessage(comp.getClientId(context), msg);
								throw new ValidatorException(msg);
							}

							else {
								System.out.println("Entered procedureID");
								Criteria cr = session.createCriteria(Payments.class);
								cr.add(Restrictions.eq("ProcedureID", value));
								cr.setProjection(Projections.rowCount());
								count = (Long) cr.uniqueResult();
								session.close();

							}
						}

					}

					else {
						FacesMessage msg = new FacesMessage("Please Enter PR followed by Digits.", uniqueColumn);
						context.addMessage(comp.getClientId(context), msg);
						throw new ValidatorException(msg);
					}

				}

			}

			if (Svalue.equalsIgnoreCase("HealthID")) {

				if (input.length() <= 0) {
					FacesMessage msg = new FacesMessage("Please Enter HealthID ID", uniqueColumn);
					context.addMessage(comp.getClientId(context), msg);
					throw new ValidatorException(msg);
				}

				else if (input.length() > 0) {
					if (input.length() >= 2) {

						if (!(input.charAt(0) == 'R' && input.charAt(1) == 'E')) {

							FacesMessage msg = new FacesMessage("Health Id should start with RE", uniqueColumn);
							context.addMessage(comp.getClientId(context), msg);
							throw new ValidatorException(msg);
						}

						else {

							boolean valid = true;
							for (int i = 2; i < input.length(); i++) {
								if (!Character.isDigit(input.charAt(i))) {
									valid = false;
								}
							}

							if (!valid) {
								FacesMessage msg = new FacesMessage("ID must be in Format 'RE0001' ", uniqueColumn);
								context.addMessage(comp.getClientId(context), msg);
								throw new ValidatorException(msg);
							}

							else {
								System.out.println("Entered Health");
								Criteria cr = session.createCriteria(Payments.class);
								cr.add(Restrictions.eq("HealthID", value));
								cr.setProjection(Projections.rowCount());
								count = (Long) cr.uniqueResult();
								session.close();

							}
						}

					}

					else {
						FacesMessage msg = new FacesMessage("Please Enter RE followed by Digits.", uniqueColumn);
						context.addMessage(comp.getClientId(context), msg);
						throw new ValidatorException(msg);
					}

				}

			}

			if (Svalue.equalsIgnoreCase("PaymentID")) {
				if (input.length()==0 || input.equals(null)) {
					FacesMessage msg = new FacesMessage("Please Enter paymentID ID", uniqueColumn);
					context.addMessage(comp.getClientId(context), msg);
					throw new ValidatorException(msg);
				}

				else if (input.length() > 0) {

					if (!(input.charAt(0) == 'T')) {
						FacesMessage msg = new FacesMessage("PaymentID should start with T", uniqueColumn);
						context.addMessage(comp.getClientId(context), msg);
						throw new ValidatorException(msg);
					}

					else {

						boolean valid = true;
						for (int i = 1; i < input.length(); i++) {
							if (!Character.isDigit(input.charAt(i))) {
								valid = false;
							}
						}

						if (!valid) {
							FacesMessage msg = new FacesMessage("ID must be in Format 'T001' ",
									uniqueColumn);
							context.addMessage(comp.getClientId(context), msg);
							throw new ValidatorException(msg);
						}

						else {
							System.out.println("Entered paymentID");
							Criteria cr = session.createCriteria(Payments.class);
							cr.add(Restrictions.eq("PaymentID", value));
							cr.setProjection(Projections.rowCount());
							count = (Long) cr.uniqueResult();
							session.close();

						}

					}

				}

			}

			if (count == 0) {

				FacesMessage msg = new FacesMessage("Entered Id is not registerd with us.", uniqueColumn);
				context.addMessage(comp.getClientId(context), msg);
				throw new ValidatorException(msg);

			}
		} catch (NoResultException ex) {
			isValid = true;
		}
	}
}
