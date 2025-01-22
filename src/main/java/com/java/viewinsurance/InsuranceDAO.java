package com.java.viewinsurance;

import java.util.List;

public interface InsuranceDAO {

//	String addInsuranceDao(Insurance insurance);
	Insurance searchByInsuranceName(String planName);
	 List showInsurance(String pVal);
}
