package com.java.healthcare.daoimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

import com.java.healthcare.entity.AvailableStatus;
import com.java.healthcare.entity.ProviderAvailability;
import com.java.healthcare.entity.SendMail;
import com.java.healthcare.idao.IAvailabilityDao;
import com.java.healthcare.sessionhelp.SessionHelper;
import com.java.project.Provider;

@ManagedBean(name = "avlDao")
@SessionScoped
public class AvailabilityDaoImpl implements IAvailabilityDao {

	// for current status in showAvailabilities page
	private String currentStatus = "";
	private String availabilityType = "";

	// getter for currentStatus
	public String getCurrentStatus() {
		return currentStatus;
	}

	// setter for current status
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	// for loading Enum values into dropdown
	public AvailableStatus[] loadAvailabilityStatus() {
		return AvailableStatus.values();
	}

	// for isRenderred tag if method return true then reschedule and cancel
	// button will show else not
	public boolean isButtonColumnShowable() {
		if (currentStatus.equals("COMPLETED") || currentStatus.equals("CANCELED"))
			return false;
		else
			return true;
	}

	// maruee tag show or not conditional render method
	public boolean isAvlTypeMaequeeShowable() {
		if (!availabilityType.equals("SINGLE_DAY") || !availabilityType.equals("RANDOM_DAYS")
				|| !availabilityType.equals("RECURING"))
			return false;
		else
			return true;
	}

	// maruee tag show or not conditional render method
	public boolean isMsgMaequeeShowable() {
		if (!availabilityType.equals("SINGLE_DAY") || !availabilityType.equals("RANDOM_DAYS")
				|| !availabilityType.equals("RECURING"))
			return true;
		else
			return false;
	}

	//single showable date render
	public boolean isSingleAvailableDateShowable() {
		if (availabilityType.equals("SINGLE_DAY"))
			return true;
		else
			return false;
	}

	//multi select date showable method
	public boolean isMultiAvailableDateShowable() {
		if (availabilityType.equals("RANDOM_DAYS"))
			return true;
		else
			return false;
	}

	//recuring select date showable
	public boolean isRecuringAvailableDateShowable() {
		if (availabilityType.equals("RECURING"))
			return true;
		else
			return false;
	}

	//show availability table is showable or not
	public boolean isShowAvailabilityTableShowable() {
		if (currentStatus.equals("COMPLETED") || currentStatus.equals("CANCELED") || currentStatus.equals("UPCOMING"))
			return true;
		else
			return false;
	}

	
	public void onAvailabilityTypeChange(ValueChangeEvent vc) {
		this.availabilityType = (String) vc.getNewValue();
	}

	// valueChangeEvent method for listerner on Availability Status change on
	// ShowAvailability.xhtml
	public void onCurrentStatusChange(ValueChangeEvent vce) {
		this.currentStatus = vce.getNewValue() == null ? "" : vce.getNewValue().toString();
	}

	// returns system default timezone and used to format table date value with
	// current System timeZone
	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	// generate availabilityId by checking database max availabilityId and
	// incrementing it
	public String generateProviderAvailabilityId() {
		SessionFactory sf = null;
		Session session = null;
		String str = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(ProviderAvailability.class);
			cr.setProjection(Projections.max("availabilityId"));
			str = (String) cr.uniqueResult();
		} finally {
			session.close();
			sf.close();
		}

		if (str == null) {
			str = "AVL001";
		}
		String sub = str.substring(3);
		int availabilityId = Integer.parseInt(sub);
		availabilityId++;
		return String.format("AVL%03d", availabilityId);
	}

	// take input as selected time in form(eg:03:30) and return the formated
	// time(eg:03:30:00) which require for the java.time.LocalTime
	public String formatInputTime(String time) {
		String formatedTime = null;
		formatedTime = time.substring(0, 2) + ":" + time.substring(3, 5) + ":00";
		System.out.println("Formated Time :" + formatedTime);
		return formatedTime;

	}

	// for info facesMessege on date select by using SelectEvent
	public void onAvlDateSelect(SelectEvent<Date> event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Available Date Selected",
				format.format(event.getObject())));
	}

	// this method fetch current Instance by external context and validate that
	// schedule with todays date and database records
	public boolean validateAvailabilitySchedule() {

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		Provider loggedProvider = (Provider) sessionMap.get("providerInfo");

		boolean isValid = true;
		
		ProviderAvailability provAvailable = (ProviderAvailability) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("provAvl");

		LocalTime startTimeForm = LocalTime.parse(formatInputTime(provAvailable.getStartTime()));
		LocalTime endTimeForm = LocalTime.parse(formatInputTime(provAvailable.getEndTime()));
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if (provAvailable.getAvailabilityType().equals("SINGLE_DAY")) {

			if (startTimeForm.compareTo(endTimeForm) >= 0) {
				isValid = false;
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error: Start-Time can not be greater than or equal to end-time ...!", null);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, message);
				return isValid;
			}
			SessionFactory sf = null;
			Session session = null;
			List<ProviderAvailability> availabilityFoundList = new ArrayList<ProviderAvailability>();
			try {
				sf = SessionHelper.getConnection();
				session = sf.openSession();
				Criteria cr = session.createCriteria(ProviderAvailability.class);
				cr.add(Restrictions.eq("providerId", loggedProvider.getProviderId()));
				cr.add(Restrictions.eq("availableDate", provAvailable.getAvailableDate()));
				cr.add(Restrictions.eq("currentStatus", AvailableStatus.UPCOMING));
				availabilityFoundList = cr.list();
			} finally {
				session.close();
				sf.close();
			}
			// System.out.println("List Size :" + availabilityFoundList.size());

			if (availabilityFoundList.size() > 0) {

				for (ProviderAvailability availabilityFound : availabilityFoundList) {
					LocalTime startTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getStartTime()));
					LocalTime endTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getEndTime()));

					if ((startTimeForm.compareTo(startTimeDb) <= 0 && endTimeForm.compareTo(startTimeDb) > 0)
							|| (startTimeForm.compareTo(endTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) >= 0)
							|| (startTimeForm.compareTo(startTimeDb) >= 0 && endTimeForm.compareTo(endTimeDb) <= 0)
							|| (startTimeForm.compareTo(startTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) > 0)) {

						isValid = false;
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error: Availability already created for Date :"
										+ sdf.format(availabilityFound.getAvailableDate()) + " and Time :" + startTimeDb
										+ " & " + endTimeDb + "..!",
								null);
						FacesContext context = FacesContext.getCurrentInstance();
						context.addMessage(null, message);
						System.out.println("FacesMessege Readed Sucessfully");
						return isValid;
					}
				}
			}
			return isValid;

		} else if (provAvailable.getAvailabilityType().equals("RANDOM_DAYS")) {

			for (Date singleAvailableDate : provAvailable.getMultiAvailableDate()) {

				if (startTimeForm.compareTo(endTimeForm) >= 0) {
					isValid = false;
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error: Start-Time can not be greater than or equal to end-time ...!", null);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, message);
					return isValid;
				}

			}

			for (Date singleAvailableDate : provAvailable.getMultiAvailableDate()) {
				SessionFactory sf = null;
				Session session = null;
				List<ProviderAvailability> availabilityFoundList = new ArrayList<ProviderAvailability>();
				try {
					sf = SessionHelper.getConnection();
					session = sf.openSession();
					Criteria cr = session.createCriteria(ProviderAvailability.class);
					cr.add(Restrictions.eq("providerId", loggedProvider.getProviderId()));
					cr.add(Restrictions.eq("availableDate", singleAvailableDate));
					cr.add(Restrictions.eq("currentStatus", AvailableStatus.UPCOMING));
					availabilityFoundList = cr.list();
				} finally {
					session.close();
					sf.close();
				}

				// System.out.println("List Size :" +
				// availabilityFoundList.size());

				if (availabilityFoundList.size() > 0) {

					for (ProviderAvailability availabilityFound : availabilityFoundList) {
						LocalTime startTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getStartTime()));
						LocalTime endTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getEndTime()));

						if ((startTimeForm.compareTo(startTimeDb) <= 0 && endTimeForm.compareTo(startTimeDb) > 0)
								|| (startTimeForm.compareTo(endTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) >= 0)
								|| (startTimeForm.compareTo(startTimeDb) >= 0 && endTimeForm.compareTo(endTimeDb) <= 0)
								|| (startTimeForm.compareTo(startTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) > 0)) {
							isValid = false;
							FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: Availability already created for Date :"
											+ sdf.format(availabilityFound.getAvailableDate()) + " and Time :"
											+ startTimeDb + " & " + endTimeDb + "..!",
									null);
							FacesContext context = FacesContext.getCurrentInstance();
							context.addMessage(null, message);
							System.out.println("FacesMessege Readed Sucessfully");
							return isValid;
						}
					}
				}

			}
			return isValid;

		} else if (provAvailable.getAvailabilityType().equals("RECURING")) {

			Date startDate = provAvailable.getRangeAvailableDate().get(0);
			Date endDate = provAvailable.getRangeAvailableDate().get(1);

			if (startDate.compareTo(endDate) >= 0) {
				isValid = false;
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error: Selected date range not valid start date must be grater than end date...!", null);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, message);
				return isValid;
			} else if (startTimeForm.compareTo(endTimeForm) >= 0) {
				isValid = false;
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error: Start-Time can not be greater than or equal to end-time ...!", null);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, message);
				return isValid;
			}

			while (startDate.compareTo(endDate) <= 0) {

				SessionFactory sf = null;
				Session session = null;

				List<ProviderAvailability> availabilityFoundList = new ArrayList<ProviderAvailability>();

				try {
					sf = SessionHelper.getConnection();
					session = sf.openSession();
					Criteria cr = session.createCriteria(ProviderAvailability.class);
					cr.add(Restrictions.eq("providerId", loggedProvider.getProviderId()));
					cr.add(Restrictions.eq("availableDate", startDate));
					cr.add(Restrictions.eq("currentStatus", AvailableStatus.UPCOMING));
					availabilityFoundList = cr.list();
				} finally {
					session.close();
					sf.close();
				}

				// System.out.println("List Size :" +
				// availabilityFoundList.size());

				if (availabilityFoundList.size() > 0) {

					for (ProviderAvailability availabilityFound : availabilityFoundList) {
						LocalTime startTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getStartTime()));
						LocalTime endTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getEndTime()));

						if ((startTimeForm.compareTo(startTimeDb) <= 0 && endTimeForm.compareTo(startTimeDb) > 0)
								|| (startTimeForm.compareTo(endTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) >= 0)
								|| (startTimeForm.compareTo(startTimeDb) >= 0 && endTimeForm.compareTo(endTimeDb) <= 0)
								|| (startTimeForm.compareTo(startTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) > 0)) {
							isValid = false;
							FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: Availability already created for Date :"
											+ sdf.format(availabilityFound.getAvailableDate()) + " and Time :"
											+ startTimeDb + " & " + endTimeDb + "..!",
									null);
							FacesContext context = FacesContext.getCurrentInstance();
							context.addMessage(null, message);
							return isValid;
						}
					}
				}
				// System.out.println("recuring while loop flow --" +
				// startDate);
				startDate = DateUtils.addDays(startDate, 1);

			}
			return isValid;
		}

		return isValid;
	}

	// this method receives validated ProviderAvailability obj and
	// currousponding available date , saves that obj to db by seting available
	// date in formated manner and generating availability id
	public void saveTransactionProvAvl(ProviderAvailability provAvl, Date singleDate) {
		provAvl.setAvailabilityId(generateProviderAvailabilityId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formatedDate = sdf.format(singleDate);
		try {
			provAvl.setAvailableDate(sdf.parse(formatedDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Transaction trans = session.beginTransaction();
			session.save(provAvl);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
	}

	// for creating provider availability, it receives the providerAvailability
	// obj ,firstly call to validateSchedule method to check wheather the
	// selected date and timings are valid or not, if valid then only it will
	// proceed further for creating the availability as per the selected type
	// single multiple or recuring and it call saveTransactionProvAvl method as
	// per the no of avaialbility creating required
	// else if selected timings are not valid then facesMessege
	// savirityType.ERROR will pop-up to UI
	@Override
	public String createProviderAvailability(ProviderAvailability providerAvailability) {
		if (validateAvailabilitySchedule() == true) {

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

			Provider loggedProvider = (Provider) sessionMap.get("providerInfo");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			providerAvailability.setProviderId(loggedProvider.getProviderId());

			if (providerAvailability.getAvailabilityType().equals("RANDOM_DAYS")) {
				SendMail.sendInfoToProvider(loggedProvider.getProviderEmailId(), "HEALTH CARE :Availability Created ",
						"You successfully created availability for Date " + providerAvailability.getMultiAvailableDate()
								+ " from " + providerAvailability.getStartTime() + " to "
								+ providerAvailability.getEndTime() + ".");
				for (Date singleDate : providerAvailability.getMultiAvailableDate()) {
					saveTransactionProvAvl(providerAvailability, singleDate);
				}

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"You successfully created availability for Date " + providerAvailability.getMultiAvailableDate()
								+ " from " + providerAvailability.getStartTime() + " to "
								+ providerAvailability.getEndTime() + ".",
						null);

				FacesContext context = FacesContext.getCurrentInstance();
				Flash flash = context.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				context.addMessage(null, message);

				return "CreateAvailability.xhtml?faces-redirect=true";
			} else if (providerAvailability.getAvailabilityType().equals("RECURING")) {
				Date startDate = providerAvailability.getRangeAvailableDate().get(0);
				Date endDate = providerAvailability.getRangeAvailableDate().get(1);
				SendMail.sendInfoToProvider(loggedProvider.getProviderEmailId(), "HEALTH CARE :Availability Created ",

						"You successfully created availability from " + sdf.format(startDate) + " to "
								+ sdf.format(endDate) + " for time " + providerAvailability.getStartTime() + " to "
								+ providerAvailability.getEndTime() + ".");
				while (startDate.compareTo(endDate) <= 0) {
					saveTransactionProvAvl(providerAvailability, startDate);
					startDate = DateUtils.addDays(startDate, 1);
				}

				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"You successfully created availability from " + sdf.format(startDate) + " to "
								+ sdf.format(endDate) + " for time " + providerAvailability.getStartTime() + " to "
								+ providerAvailability.getEndTime() + ".",
						null);

				FacesContext context = FacesContext.getCurrentInstance();
				Flash flash = context.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				context.addMessage(null, message);

				return "CreateAvailability.xhtml?faces-redirect=true";
			}
			SendMail.sendInfoToProvider(loggedProvider.getProviderEmailId(), "HEALTH CARE :Availability Created ",
					"You successfully created availability for date "
							+ sdf.format(providerAvailability.getAvailableDate()) + " from "
							+ providerAvailability.getStartTime() + " to " + providerAvailability.getEndTime() + ".");
			saveTransactionProvAvl(providerAvailability, providerAvailability.getAvailableDate());

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"You successfully created availability for date "
							+ sdf.format(providerAvailability.getAvailableDate()) + " from "
							+ providerAvailability.getStartTime() + " to " + providerAvailability.getEndTime() + ".",
					null);

			FacesContext context = FacesContext.getCurrentInstance();
			Flash flash = context.getExternalContext().getFlash();
			flash.setKeepMessages(true);
			context.addMessage(null, message);

			return "CreateAvailability.xhtml?faces-redirect=true";
		}
		return "";
	}

	@Override
	public List<ProviderAvailability> showAvailabilityDetails() {

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		Provider loggedProvider = (Provider) sessionMap.get("providerInfo");

		List<ProviderAvailability> avalabilityList = new ArrayList<ProviderAvailability>();
		List<ProviderAvailability> deletingRecords = new ArrayList<ProviderAvailability>();
		SessionFactory sf = null;
		Session session = null;

		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(ProviderAvailability.class);
			cr.add(Restrictions.eq("providerId", loggedProvider.getProviderId()));

			if (currentStatus.equals("COMPLETED"))
				cr.add(Restrictions.eq("currentStatus", AvailableStatus.COMPLETED));
			else if (currentStatus.equals("CANCELED"))
				cr.add(Restrictions.eq("currentStatus", AvailableStatus.CANCELED));
			else
				cr.add(Restrictions.eq("currentStatus", AvailableStatus.UPCOMING));

			avalabilityList = cr.list();
			deletingRecords = new ArrayList<ProviderAvailability>();
		} catch (HibernateException e1) {
			e1.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		Date todaysDate = new Date();
		for (ProviderAvailability provAvl : avalabilityList) {

			try {
				if (dateFormatter.parse(dateFormatter.format(provAvl.getAvailableDate()))
						.compareTo(dateFormatter.parse(dateFormatter.format(todaysDate))) < 0
						&& provAvl.getCurrentStatus().toString().equals("UPCOMING")) {

					// System.out.println("setting completed status of ::::" +
					// provAvl.getAvailabilityId());
					provAvl.setCurrentStatus(AvailableStatus.COMPLETED);
					try {
						sf = SessionHelper.getConnection();
						session = sf.openSession();
						Transaction trans = session.beginTransaction();
						session.update(provAvl);
						trans.commit();
					} finally {
						session.close();
						sf.close();
					}

					deletingRecords.add(provAvl);
				}
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return avalabilityList;
	}

	public boolean validateUpdatingSchedule(ProviderAvailability updateAvailability) {

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Provider loggedProvider = (Provider) sessionMap.get("providerInfo");

		boolean isValid = true;

		LocalTime startTimeForm = LocalTime.parse(formatInputTime(updateAvailability.getStartTime()));
		LocalTime endTimeForm = LocalTime.parse(formatInputTime(updateAvailability.getEndTime()));

		if (startTimeForm.compareTo(endTimeForm) >= 0) {
			isValid = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error: Start-Time can not be greater than or equal to end-time ...!", null);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("updtMsg", message);
			return isValid;
		}
		SessionFactory sf = null;
		Session session = null;

		List<ProviderAvailability> availabilityFoundList = new ArrayList<ProviderAvailability>();
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(ProviderAvailability.class);
			cr.add(Restrictions.eq("providerId", loggedProvider.getProviderId()));
			cr.add(Restrictions.eq("availableDate", updateAvailability.getAvailableDate()));
			cr.add(Restrictions.eq("currentStatus", AvailableStatus.UPCOMING));
			availabilityFoundList = cr.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}

		// System.out.println("List Size :" + availabilityFoundList.size());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
		if (availabilityFoundList != null && availabilityFoundList.size() > 0) {

			for (ProviderAvailability availabilityFound : availabilityFoundList) {
				LocalTime startTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getStartTime()));
				LocalTime endTimeDb = LocalTime.parse(formatInputTime(availabilityFound.getEndTime()));

				if (!updateAvailability.getAvailabilityId().equals(availabilityFound.getAvailabilityId())) {
					if ((startTimeForm.compareTo(startTimeDb) <= 0 && endTimeForm.compareTo(startTimeDb) > 0)
							|| (startTimeForm.compareTo(endTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) >= 0)
							|| (startTimeForm.compareTo(startTimeDb) >= 0 && endTimeForm.compareTo(endTimeDb) <= 0)
							|| (startTimeForm.compareTo(startTimeDb) < 0 && endTimeForm.compareTo(endTimeDb) > 0)) {
						isValid = false;
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error: Availability already created for Date :"
										+ sdf.format(availabilityFound.getAvailableDate()) + " and Time :" + startTimeDb
										+ " & " + endTimeDb + "..!",
								null);
						FacesContext context = FacesContext.getCurrentInstance();
						context.addMessage(null, message);
						// System.out.println("FacesMessege Readed
						// Sucessfully");
						return isValid;
					}
				}
			}
		}
		return isValid;
	}

	@Override
	public String updateProviderAvailability(ProviderAvailability updateAvailability) {

		boolean res = validateUpdatingSchedule(updateAvailability);
		System.out.println(res);
		if (res == true) {
			SessionFactory sf = null;
			Session session = null;

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			Criteria cr;
			Provider loggedProvider = (Provider) sessionMap.get("providerInfo");

			ProviderAvailability oldAvailability = null;
			try {
				sf = SessionHelper.getConnection();
				session = sf.openSession();
				cr = session.createCriteria(ProviderAvailability.class);
				cr.add(Restrictions.eq("availabilityId", updateAvailability.getAvailabilityId()));
				oldAvailability = (ProviderAvailability) cr.uniqueResult();
			} catch (HibernateException e) {
				e.printStackTrace();
			} finally {
				session.close();
				sf.close();
			}

			try {
				sf = SessionHelper.getConnection();
				session = sf.openSession();
				Transaction trans = session.beginTransaction();
				session.update(updateAvailability);
				trans.commit();
			} catch (HibernateException e) {
				e.printStackTrace();
			} finally {
				session.close();
				sf.close();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SendMail.sendInfoToProvider(loggedProvider.getProviderEmailId(), "HEALTH CARE :Availability Updated ",
					"You successfully Updated availability from date :" + sdf.format(oldAvailability.getAvailableDate())
							+ "[ " + oldAvailability.getStartTime() + "-" + oldAvailability.getEndTime()
							+ " ] to Date :" + sdf.format(updateAvailability.getAvailableDate()) + "[ "
							+ updateAvailability.getStartTime() + "-" + updateAvailability.getEndTime() + " ]");

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"You successfully Updated availability from date :" + sdf.format(oldAvailability.getAvailableDate())
							+ "[ " + oldAvailability.getStartTime() + "-" + oldAvailability.getEndTime()
							+ " ] to Date :" + sdf.format(updateAvailability.getAvailableDate()) + "[ "
							+ updateAvailability.getStartTime() + "-" + updateAvailability.getEndTime() + " ]",
					null);

			FacesContext context = FacesContext.getCurrentInstance();
			Flash flash = context.getExternalContext().getFlash();
			flash.setKeepMessages(true);
			context.addMessage(null, message);

			return "ShowAvailabilities.xhtml?faces-redirect=true";
		}

		return "";

	}

	@Override
	public String searchProviderAvailabilityById(String availabilityId) {

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		ProviderAvailability availabilityFound = new ProviderAvailability();
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(ProviderAvailability.class);
			cr.add(Restrictions.eq("availabilityId", availabilityId));
			availabilityFound = (ProviderAvailability) cr.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		sessionMap.put("availabilityFound", availabilityFound);
		return "UpdateAvailability.xhtml?faces-redirect=true";
	}

	@Override
	public String cancelProviderAvailability(String availabilityId) {

		ProviderAvailability availabilityFound = searchProviderAvailability(availabilityId);
		availabilityFound.setCurrentStatus(AvailableStatus.CANCELED);
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Transaction trans = session.beginTransaction();
			session.update(availabilityFound);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Availability with Id " + availabilityId
				+ " and date " + sdf.format(availabilityFound.getAvailableDate()) + " has been CANCELLED Successfully",
				null);

		FacesContext context = FacesContext.getCurrentInstance();
		Flash flash = context.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		context.addMessage(null, message);

		return "ShowAvailabilities.xhtml?faces-redirect=true";
	}

	@Override
	public ProviderAvailability searchProviderAvailability(String availabilityId) {
		ProviderAvailability availabilityFound = new ProviderAvailability();
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(ProviderAvailability.class);
			cr.add(Restrictions.eq("availabilityId", availabilityId));
			availabilityFound = (ProviderAvailability) cr.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return availabilityFound;
	}

}
