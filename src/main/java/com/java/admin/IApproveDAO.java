package com.java.admin;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.java.loginProvider.Login;
import com.java.project.Provider;

public interface IApproveDAO {
	/*
	 * date expiry date status npiid username
	 * 
	 */

	List<Provider> showAllForApproveProvider();

	String searchProvider(String providerId);
	// void doValidate(String providerId);

	boolean doSpecializationValidation(String providerId);

	boolean doEducationValidation(String providerId);

	boolean doCouncilsValidation(String providerId);

	boolean doCategoryValidation(String providerId);

	boolean doLicenceIdValidation(String providerId);

	boolean doAadharValidation(String providerId);

	boolean dateOfBirthValidation(String providerId);

	List<Provider> showAllSuspendedProvider();

	String generateNPIID(Provider provider);

	Provider searchProviderById(String providerId);

	Date calculateExpireDate(int year);

	Date calculateExtendedTime(int month);

	void firstTimeApprove(Provider provider, Login login);

	void firstTimeSuspend(Provider provider, Login login);

	void firstTimeReject(Provider provider, Login login);
	
	void reActivateProvider(Provider provider, Login login);
	
	String approveProvider(Provider provider, Login login) throws ParseException;
	
	String addProviderComment(Provider provider) ;
	
	

}
