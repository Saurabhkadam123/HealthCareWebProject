package com.java.admin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.java.loginProvider.Login;
import com.java.project.Provider;
import com.java.project.SessionHelper;
import com.java.project.Status;

@ManagedBean(name = "aDao")
@RequestScoped
public class ApprovePDAOImpl implements IApproveDAO {

	// StringBuilder errorList = new StringBuilder();

	static StringBuilder errorlist;
	static {
		errorlist = new StringBuilder();
	}

	boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7= false;

	@Override
	public List<Provider> showAllForApproveProvider() {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		cr.add(Restrictions.eq("status", Status.PENDING));
		Transaction t = session.beginTransaction();
		List<Provider> list = cr.list();
		// session.save(provider);
		// t.commit();
		return list;
	}

	@Override
	public List<Provider> showAllSuspendedProvider() {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		cr.add(Restrictions.eq("status", Status.SUSPENDED));
		Transaction t = session.beginTransaction();
		List<Provider> list = cr.list();
		// session.save(provider);
		// t.commit();
		return list;
	}

	// call after review method
	@Override
	public String searchProvider(String providerId) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		cr.add(Restrictions.eq("providerId", providerId));
		Provider provider = (Provider) cr.uniqueResult();
		sessionMap.put("oneProvider", provider);
		return "ShowSingleRecord.xhtml?faces-redirect=true";
	}

	@Override
	public String generateNPIID(Provider provider) {
		SessionFactory sfactory = SessionHelper.getConnection();
		Session session = sfactory.openSession();
		Criteria criteria = session.createCriteria(Provider.class).setProjection(Projections.max("npiId"));
		String str = (String) criteria.uniqueResult();
		if (str == null) {
			return "NPI0000001";
		}
		String order = str.substring(3);
		long npiId = Long.parseLong(order);
		npiId++;

		return String.format("NPI%07d", npiId);
	}

	public static int generateOtp() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

	public long calculatBalDays(Provider provider) {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
		Date date = new Date();
		String inputString1 = myFormat.format(date);
		System.out.println("Current date " + inputString1);
		Date timLine = provider.getSuspendedDate();
		String inputString2 = myFormat.format(timLine);
		System.out.println("Time Line Date " + inputString2);
		try {
			Date date1 = myFormat.parse(inputString1);
			Date date2 = myFormat.parse(inputString2);
			long diff = date2.getTime() - date1.getTime();
			long diff2 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			System.out.println("Days: " + diff2);
			return diff2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void firstTimeApprove(Provider provider, Login login) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		Transaction trans = session.beginTransaction();
		// addProviderEntity(provider.getProviderId(), login);
		provider.setStatus(Status.ACTIVE);
		String email = provider.getProviderEmailId();
		String username1 = provider.getUsername();

		provider.getLogin().setFlag("YES");
		String str = provider.getUsername();
		// int otpNew1 = generateOtp();
		String otpNew = "" + generateOtp();
		provider.getLogin().setPassword(otpNew);
		// login.setPassword(otpNew);
		String npiId = generateNPIID(provider);
		provider.setNpiId(npiId);
		String name = provider.getFristName();
		Sendmail.sendInfo(email, "Congratulations and best wishes for a successful provider approval ",
				"Dear " + name
						+ ",Your application is accepted sucessfully. Now you are provider.login using generated OTP and username. your ID is : "
						+ npiId);
		Sendmail.sendInfo(email, "Your OTP are Generated", "Generated Otp is  " + otpNew);
		Sendmail.sendInfo(email, "Your username  ", "  username is   " + str);
		provider.getLogin().setUsername(username1);

		provider.getLogin().setProviderEmailId(email);
		provider.setRegisterDate(new Date());
		Date exDatep = provider.getExpiryDate();
		exDatep = calculateExpireDate(3);
		provider.setExpiryDate(exDatep);
		session.update(provider);
		trans.commit();

	}

	@Override
	public void firstTimeSuspend(Provider provider, Login login) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		provider.setStatus(Status.SUSPENDED);
		String email = provider.getProviderEmailId();
		provider.setSuspendedDate(calculateExtendedTime(1));
		String name = provider.getFristName();
		Sendmail.sendInfo(email, "Your application is suspended ",
				"Dear " + name
						+ ", Thank you for your application for the provider role.We really appreciate your interest we’re pleased that you decided to invest time and effort in applying. but due to follwing reasons "
						+ errorlist.toString()
						+ " your application are rejected. please update your valid documents again before "
						+ calculateExtendedTime(1));
		sessionMap.put("oneProvider", provider);
	}

	@Override
	public void firstTimeReject(Provider provider, Login login) {
		// TODO Auto-generated method stub
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction trans = session.beginTransaction();
		String email = provider.getProviderEmailId();
		String user = provider.getUsername();
		provider.setStatus(Status.REJECTED);
		session.update(provider);
		trans.commit();
		session.close();
		Sendmail.sendInfo(email, "Your application is rejected ",
				"Your application is rejected due to you does not update your details withthin given days");

	}

	@Override
	public void reActivateProvider(Provider provider, Login login) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction trans = session.beginTransaction();
		Criteria cr = session.createCriteria(Provider.class);
		provider.setStatus(Status.ACTIVE);
		Date newExDate = calculateExpireDate(3);
		provider.setExpiryDate(newExDate);
		String email = provider.getProviderEmailId();
		String name = provider.getFristName();
		Sendmail.sendInfo(email, "Congratulations on Reactivation",
				"Dear " + name + "Your application is Reactivated . your new expiry date are : " + newExDate);
		session.update(provider);
		trans.commit();
		session.close();
	}

	@Override
	public String approveProvider(Provider provider, Login login) throws ParseException {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		int fk = provider.getFkId();
		Criteria cr2 = session.createCriteria(Login.class);
		cr2.add(Restrictions.eq("userId", fk));
		Login p1 = (Login) cr2.uniqueResult();
		provider.setLogin(p1);
		sessionMap.put("oneProvider", provider);

		// validation method call

		boolean isvalid = doValid(provider.getProviderId());
		// IF PROVIDERS EXPIRY DATE END THEN PROVIDER STATUS WILL BE SET PENDING WHILE LOGIN AND 
		// AFTER VALIDATING ALL FIELDS PROVIDER NEW EXPIRY DATE WILL SET BY ADMIN.
		if (provider.getExpiryDate() != null && isvalid == true) {
			System.out.println("for expire ");
			reActivateProvider(provider, login);

			return "Approved.xhtml?faces-redirect=true";

		}
		// first time approve

		else if (provider.getStatus().equals(Status.PENDING) && isvalid == true) {
			System.out.println("for first time ");
			firstTimeApprove(provider, login);

			// session.close();

			return "Approved.xhtml?faces-redirect=true";
		}
		// first time suspend
		else if (provider.getStatus().equals(Status.PENDING) && isvalid == false) {
			System.out.println(" firstTimeSuspend Else block-----------------------");
			firstTimeSuspend(provider, login);

			return "AddComment.xhtml?faces-redirect=true";
		}
		// approverd after suspended
		else if (provider.getStatus().equals(Status.SUSPENDED) && isvalid == true) {
			firstTimeApprove(provider, login);
			return "Approved.xhtml?faces-redirect=true";

		}
		// suspend if within date
		/*
		 * else if (provider.getStatus().equals(Status.SUSPENDED) && isvalid ==
		 * false && (calculatBalDays(provider)) > 0) {
		 * firstTimeSuspend(provider, login); return
		 * "AddComment.xhtml?faces-redirect=true";
		 * 
		 * }
		 */

		else if (provider.getStatus().equals(Status.SUSPENDED) && isvalid == false && calculatBalDays(provider) <= 0) {
			firstTimeReject(provider, login);
			return "Rejected.xhtml?faces-redirect=true";

		}

		// CHECK THIS CONDTION
		return "Suspended.xhtml?faces-redirect=true";

	}

	// SEARCH PROVIDER BY ID
	@Override
	public Provider searchProviderById(String providerId) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		// cr.add(Restrictions.eq("key_table", ))
		cr.add(Restrictions.eq("providerId", providerId));
		// System.out.println("list test_______"+cr.list().get(0));
		Provider provider = (Provider) cr.uniqueResult();

		return provider;
	}

	// PROVIDER EXPIRY DATE CALCULTION (COMPAIR WITH TODAYS DATE)
	@Override
	public Date calculateExpireDate(int year) {
		DateFormat datefor = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(cal.YEAR, year);
		return cal.getTime();
	}

	// EXTENDED TIME FOR UPDATE DETAILS OF PROVIDER COMPAIR WITH TODAYS
	// DATE(E.G. ONE MONTH)
	@Override
	public Date calculateExtendedTime(int month) {
		DateFormat datefor = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, month);
		return cal.getTime();
	}
	// VALIDATION STRING BUILDER
	// BECAUSE STRING BUILDRE ARE IMUTABLE

	public boolean doValid(String providerId) throws ParseException {
		boolean isValid = true;
		errorlist.setLength(0);
		if (flag1 == doCategoryValidation(providerId)) {
			errorlist.append(System.getProperty("line.separator"));
			errorlist.append("Provider's Category not valid.");
			errorlist.append(System.getProperty("line.separator"));
			isValid = false;
		}
		if (flag2 == doCouncilsValidation(providerId)) {
			errorlist.append("Council not match. ");
			errorlist.append(System.getProperty("line.separator"));
			isValid = false;
		}
		if (flag3 == doEducationValidation(providerId)) {
			errorlist.append("Education not valid. ");
			errorlist.append(System.getProperty("line.separator"));
			isValid = false;
		}

		if (flag4 == doSpecializationValidation(providerId)) {
			errorlist.append("Specialization not match. ");
			errorlist.append(System.getProperty("line.separator"));
			isValid = false;
		}
		if (flag5 == doLicenceIdValidation(providerId)) {
			errorlist.append("LicenceId not valid. ");
			errorlist.append(System.getProperty("line.separator"));
			isValid = false;
		}
		if (flag6 == doAadharValidation(providerId)) {
			errorlist.append("Aadhar card details are not valid. ");
			errorlist.append(System.getProperty("line.separator"));
			isValid = false;
		}
		if (flag7 == dateOfBirthValidation(providerId)) {
			errorlist.append("your age and experience are not match with required fields, please update valid one. ");
			errorlist.append(System.getProperty("line.separator"));
			isValid = false;
		}
	

		System.out.println(errorlist.toString());
		return isValid;
	}

	// SPECIALIZATION VALIDATION
	// LIST USED FOR FETCH DATA FROM DB
	@Override
	public boolean doSpecializationValidation(String providerId) {

		Provider provider = searchProviderById(providerId);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(AdminEn.class);
		cr.add(Restrictions.eq("type", "speciality"));
		List<AdminEn> list = cr.list();
		System.out.println(list.size());
		// AdminEn adminEn = (AdminEn) cr.uniqueResult();
		// String value=refppjo.getType().;
		// String value="mbbs";
		boolean flag = false;
		for (AdminEn adminEn2 : list) {
			System.out.println("admin pojo ---------------------" + adminEn2.getValue());
			System.out.println("provider pojo ---------------------" + provider.getSpeciality());

			if (adminEn2.getValue().equals(provider.getSpeciality())) {
				flag = true;
				break;
			}
		}

		if (flag == true) {
			System.out.println("Specialization Found--------------------------");
		} else {
			System.out.println("Specialization Not Found---------------------");
		}
		return flag;

	}

	// EDUCATION VALIDATION
	// LIST USED FOR FECH DATA FROM DB AND COMPAIR WITH PROVIDER INSERTED DATA
	@Override
	public boolean doEducationValidation(String providerId) {

		Provider provider = searchProviderById(providerId);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(AdminEn.class);
		cr.add(Restrictions.eq("type", "education"));
		List<AdminEn> list = cr.list();
		System.out.println(list.size());
		// AdminEn adminEn = (AdminEn) cr.uniqueResult();
		// String value=refppjo.getType().;
		// String value="mbbs";
		boolean flag = false;
		for (AdminEn adminEn2 : list) {
			System.out.println("admin pojo ---------------------" + adminEn2.getValue());
			System.out.println("provider pojo ---------------------" + provider.getEducation());

			if (adminEn2.getValue().equals(provider.getEducation())) {
				flag = true;
				break;
			}
		}

		if (flag == true) {
			System.out.println("Education Found");
		} else {
			System.out.println("Education Not Found");
		}
		return flag;

	}
	// COUNCILS VALIDATION METHOD

	@Override
	public boolean doCouncilsValidation(String providerId) {

		Provider provider = searchProviderById(providerId);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(AdminEn.class);
		cr.add(Restrictions.eq("type", "Councils"));
		List<AdminEn> list = cr.list();
		System.out.println(list.size());
		// AdminEn adminEn = (AdminEn) cr.uniqueResult();
		// String value=refppjo.getType().;
		// String value="mbbs";
		boolean flag = false;
		for (AdminEn adminEn2 : list) {
			System.out.println("admin pojo ---------------------" + adminEn2.getValue());
			System.out.println("provider pojo ---------------------" + provider.getCouncil());

			if (adminEn2.getValue().equals(provider.getCouncil())) {
				flag = true;
				break;
			}
		}

		if (flag == true) {
			System.out.println("Council Found");
		} else {
			System.out.println("Council Not Found");
		}
		return flag;

	}

	// CATEGORY VALIDATION METHOD
	@Override
	public boolean doCategoryValidation(String providerId) {

		Provider provider = searchProviderById(providerId);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(AdminEn.class);
		cr.add(Restrictions.eq("type", "Category"));
		List<AdminEn> list = cr.list();
		System.out.println(list.size());
		// AdminEn adminEn = (AdminEn) cr.uniqueResult();
		// String value=refppjo.getType().;
		// String value="mbbs";
		boolean flag = false;
		for (AdminEn adminEn2 : list) {
			System.out.println("admin pojo ---------------------" + adminEn2.getValue());
			System.out.println("provider pojo ---------------------" + provider.getCategory());

			if (adminEn2.getValue().equals(provider.getCategory())) {
				flag = true;
				break;
			}
		}

		if (flag == true) {
			System.out.println("Category Found");
		} else {
			System.out.println("Category Not Found");
		}
		return flag;

	}

	// LICENCE VALIDATION METHOD
	// \\D CHECKS ONLY 4 DIGIT ARE PRESENT IN GIVEN DATA
	@Override
	public boolean doLicenceIdValidation(String providerId) {
		boolean flag = false;
		Provider provider = searchProviderById(providerId);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		String ln = provider.getLicenseId();
		String regex = "\\d+";
		while (true) {

			if (ln.length() == 4 && ln.matches(regex)) {
				System.out.println("inside valid input====================== " + ln);
				return true;

			} else {
				System.out.println("please input a valid 4 digit licenceID");
				return false;
			}

		}

	}
	// DATE OF BIRTH VALIDATION
	// PROVIDER AGE IS GTEATER THAN 23 YEARS OLD

	@Override
	public boolean dateOfBirthValidation(String providerId) {
		boolean flag = false;
		Provider provider = searchProviderById(providerId);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Date dob = provider.getProviderDob();
		int experence = Integer.parseInt(provider.getExperience());
		System.out.println("----------------------dob" + dob);
		Date curDate = new Date();
		System.out.println("-------------curDate-----------------" + curDate);
		int age = curDate.getYear() - dob.getYear();
		System.out.println("----------------------age-------------------------" + age);
		int ageReqForEdu=23;
	
		if (ageReqForEdu + experence >= age) {
				return false;
		}
	
		return true;
	}
	// PROVIDERS AADHAR CARD VALIDATION
	// NUMBER SHOULD NOT START BY 0 OR 1
	// ALSO NUMBER LENGTH ==12 DIGIT

	@Override
	public boolean doAadharValidation(String providerId) {
		boolean flag = false;
		Provider provider = searchProviderById(providerId);
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		String ln = provider.getAadharNo();
		System.out.println("aadhar no --------------------------------" + ln);
		// Regex to check valid Aadhaar number.
		String regex = "^[2-9]{1}[0-9]{11}$";
		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the string is empty
		// return false
		if (ln == null) {
			return false;
		}

		// Pattern class contains matcher() method
		// to find matching between given string
		// and regular expression.
		Matcher m = p.matcher(ln);

		// Return if the string
		// matched the ReGex
		return m.matches();

	}

	// ADD COMMENT TO PROVIDER IF SOME INVALID DOCUMENTS SUBMITTED BY PROVIDER
	@Override
	public String addProviderComment(Provider provider) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);

		Transaction t = session.beginTransaction();
		session.update(provider);
		t.commit();
		return "Suspended.xhtml?faces-redirect=true";
	}
}

	