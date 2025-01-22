package com.java.loginProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.java.healthcare.sessionhelp.SessionHelper;
import com.java.project.Provider;
import com.java.project.Status;

@ManagedBean(name = "lDao")
@SessionScoped
public class LoginDaoImpl implements LoginDao {

	private String newPass;

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	// -----------------------add user-------------------------------
	public static int generateOtp() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

	@Override
	public long otpGenerate(String userName) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Login.class);
		cr.add(Restrictions.eq("userName", userName));
		cr.setProjection(Projections.rowCount());
		long count = (Long) cr.uniqueResult();
		System.out.println(count);
		return count;
	}

	@Override
	public Login searchByLoginUser(String userName) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Login.class);
		cr.add(Restrictions.eq("username", userName));
		Login user = (Login) cr.uniqueResult();
		System.out.println("Searched......" + user);
		return user;
	}

	public Provider searchByUserId(int uId) {

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Provider.class);
		cr.add(Restrictions.eq("fkId", uId));
		Provider pro = (Provider) cr.uniqueResult();
		System.out.println("Searched......");
		return pro;

	}

	public boolean checkExpireDate(Login loginFound) {

		int uId = loginFound.getUserId();
		System.out.println("userName is : " + uId);
		Provider p = searchByUserId(uId);

		Date expDate = p.getExpiryDate();
		Date date = new Date();

		long balDay = calculateDays(expDate, date);

		if (balDay > 1) {
			return true;
		} else {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Transaction trans = session.beginTransaction();
			// Date ex=calculateExpireDate(3);

			p.setStatus(Status.PENDING);
			session.update(p);
			trans.commit();

			return false;
		}

	}

	// Calculate days
	public long calculateDays(Date expDate, Date crDate) {

		SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
		Date date = crDate;
		String inputString1 = myFormat.format(date);
		System.out.println("Current date " + inputString1);
		Date timLine = expDate;
		String inputString2 = myFormat.format(timLine);
		System.out.println("Time Line Date " + inputString2);
		try {
			Date date1 = myFormat.parse(inputString1);
			Date date2 = myFormat.parse(inputString2);
			// retuens in ml sec
			long diff = date2.getTime() - date1.getTime();
			long diff2 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

			System.out.println("Days: " + diff2);
			return diff2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;

	}

	// ----------------one time password validation
	// method-----------------------------------
	@Override
	public String validateOtp(Login login) {
		/*
		 * System.out.println("oooooooooooooooooooooo");
		 */ Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		/*
		 * System.out.println("Inside validate method");
		 */ String uName = login.getUsername();
		/*
		 * System.out.println("Uname"+uName);
		 */ Login loginFound = searchByLoginUser(uName);
		String email = loginFound.getProviderEmailId();

		String uNameFromDatebase = loginFound.getUsername();

		if (!uNameFromDatebase.equals(uName)) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("Please enter a valid username!!!");
			facesContext.addMessage("frm:username", facesMessage);
			return "";
		}
		boolean expStat = checkExpireDate(loginFound);
		if (expStat == true) {
			if (loginFound.getFlag().equals("NO")) {
				Criteria cr = session.createCriteria(Login.class);
				String encr = EntryptPassword.getCode(login.getPassword());
				cr.add(Restrictions.eq("username", loginFound.getUsername()));
				cr.add(Restrictions.eq("password", encr.trim()));
				cr.setProjection(Projections.rowCount());
				long count = (Long) cr.uniqueResult();
				session.close();
				try {
					if (count == 1) {
						sessionMap.put("username", loginFound.getUsername());

						session = sf.openSession();
						cr = session.createCriteria(Provider.class);
						cr.add(Restrictions.eq("username", loginFound.getUsername()));
						Provider loggedProvider = (Provider) cr.uniqueResult();
						session.close();
						Map<String, Object> sessMap = FacesContext.getCurrentInstance().getExternalContext()
								.getSessionMap();
						sessMap.put("providerInfo", loggedProvider);
						return "HomePageNew.xhtml?faces-redirect=true";
					} else {
						FacesContext facesContext = FacesContext.getCurrentInstance();
						FacesMessage facesMessage = new FacesMessage("Please enter a valid Password !!!");
						facesContext.addMessage("frm:password", facesMessage);

						// ===========================================================

					}
				} catch (HibernateException e) {
					e.printStackTrace();

				}
			}
			if (loginFound.getFlag().equals("YES")) {
				try {
					if (loginFound.getPassword().equals(login.getPassword())) {
						sessionMap.put("username", loginFound.getUsername());
						return "reset.xhtml?faces-redirect=true";
					} else {
						FacesContext facesContext = FacesContext.getCurrentInstance();
						FacesMessage facesMessage = new FacesMessage("Please enter a valid OTP!!!");
						facesContext.addMessage("frm:password", facesMessage);
					}
				} catch (HibernateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Provider p = searchByUserId(loginFound.getUserId());

			Sendmail1.sendInfo(email, "Provider Validity Expired",
					"Dear " + p.getFristName() + " " + p.getLastName() + "\n" + "Your Provider validity is expired for "
							+ p.getHospital() + " Hospital wait until admin renewal");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("Your validity expired..!!");
			facesContext.addMessage("frm:password", facesMessage);

			return "";
		}

		return null;
	}

	// --------------------reset password--------------------------------------

	public String resetPassword(Login login) {
		String encr = EntryptPassword.getCode(login.getPassword());// .getPassCode());
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		try {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Criteria cr = session.createCriteria(Login.class);
			cr.add(Restrictions.eq("username", login.getUsername()));// .getUserName()));
			List<Login> list = cr.list();
			Login login2 = list.get(0);
			login2.setFlag("NO");
			login2.setPassword(encr);
			Transaction t = session.beginTransaction();
			session.update(login2);
			t.commit();
			return "ConfirmMessage.xhtml?faces-redirect=true";
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "reset.xhtml?faces-redirect=true";
	}

	public String changePassword(Login login, String newPassword) {
		String encrNew = EntryptPassword.getCode(newPassword);
		String encr = EntryptPassword.getCode(login.getPassword());
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Login.class);
		cr.add(Restrictions.eq("username", login.getUsername()));
		cr.add(Restrictions.eq("password", encr.trim()));
		cr.setProjection(Projections.rowCount());
		long count = (Long) cr.uniqueResult();

		if (count == 1) {

			cr = session.createCriteria(Login.class);
			cr.add(Restrictions.eq("username", login.getUsername()));
			Login loginFound = (Login) cr.uniqueResult();
			// loginFound.setPassword(login.getPassword());
			Transaction tran = session.beginTransaction();

			loginFound.setFlag("NO");
			loginFound.setPassword(encrNew);
			session.update(loginFound);
			tran.commit();

			return "ConfirmMessageOld.xhtml?faces-redirect=true";

		} else {
			sessionMap.put("err", "You Have Entered Wrong Old Password");
			return "changePassword.xhtml?faces-redirect=true";
		}
	}

	// ----------------------forget password--------------------------
	@Override
	public String otp(Login login) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Login.class);

		/*
		 * Login userFound = searchByLoginUser(login.getUsername()); String
		 * email = userFound.getProviderEmailId();
		 */
		Login userFound = searchByLoginUser(login.getUsername());
		if (userFound == null) {
			sessionMap.put("errMsg", "Wrong Username");
			return "sendOtp.xhtml?faces-redirect=true";
		}

		String email = userFound.getProviderEmailId();

		long otpNew = generateOtp();
		String otp1 = "";
		otp1 = otp1 + otpNew;
		System.out.println(generateOtp());
		System.out.println("Email is " + email);
		Sendmail1.sendInfo(email, "Your OTP Generated", "Generated Otp is " + otp1);
		Transaction tr = session.beginTransaction();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 2);
		Date exdate = c.getTime();
		userFound.setOtp(otp1);
		userFound.setOtpExpiryTime(exdate);
		userFound.setOtpSendTime(new Date());

		session.update(userFound);
		tr.commit();
		return "otpLogin.xhtml?faces-redirect=true";
	}

	@Override
	public String forgetPasswordDao(Login user) {
		String profilePassword = EntryptPassword.getCode(user.getUsername());
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Login.class);
		cr.add(Restrictions.eq("username", user.getUsername()));
		// cr.add(Restrictions.eq("profilePassword", profilePassword.trim()));
		cr.setProjection(Projections.rowCount());
		long count = (Long) cr.uniqueResult();
		System.out.println(count);
		if (count == 1) {
			// user.setPassword(null);
			return "Login.xhtml?faces-redirect=true";
		} else {
			return "Failure.xhtml?faces-redirect=true";
		}

	}

	@Override
	public String ResetPasswordDao(Login newUser) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr1 = session.createCriteria(Login.class);
		cr1.add(Restrictions.eq("username", newUser.getUsername()));
		int count1 = cr1.list().size();
		if (count1 == 1) {
			Login loginFound = (Login) cr1.uniqueResult();
			System.out.println(loginFound);
			int loginId = loginFound.getUserId();
			System.out.println("Login Id is  " + loginId);
			loginFound.setPassword(EntryptPassword.getCode(newUser.getPassword()));
			Transaction trans = session.beginTransaction();
			session.update(loginFound);
			trans.commit();
			return "Login.xhtml?faces-redirect=true";

		} else {
			sessionMap.put("error", "Invalid Credentials...");
			return "ResetPassword.xhtml?faces-redirect=true";
		}

	}

	private Login findObjectuser(int existiingUserId) {
		Login login = new Login();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Login.class);
		cr.add(Restrictions.eq("loginId", existiingUserId));
		login = (Login) cr.uniqueResult();
		return login;
	}

	@Override
	public String validateOtp1(Login login) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Login.class);
		cr.add(Restrictions.eq("username", login.getUsername()));
		cr.add(Restrictions.eq("Otp", login.getOtp()));
		cr.setProjection(Projections.rowCount());
		long count = (Long) cr.uniqueResult();

		Login userFound = searchByLoginUser(login.getUsername());

		if ((new Date()).before(userFound.getOtpExpiryTime())) {
			if (count == 1) {
				return "UserForgetPassword.xhtml?faces-redirect=true";
			} else {
				sessionMap.put("error", "You Entered Wrong OTP....");
				return "You Entered Wrong OTP....";
			}
		}

		else {
			sessionMap.put("error", "Time Out.....");
			System.out.println("Time Out.....");
			return "sendOtp.xhtml?faces-redirect=true";
		}

	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Login.xhtml?faces-redirect=true";
	}

}