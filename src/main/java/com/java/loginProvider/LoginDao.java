package com.java.loginProvider;

public interface LoginDao {

	String validateOtp(Login login);

	String resetPassword(Login login) throws ClassNotFoundException;

	Login searchByLoginUser(String userName);

	long otpGenerate(String userName);

	String otp(Login login);

	String forgetPasswordDao(Login user);

	String ResetPasswordDao(Login newUser);

	String validateOtp1(Login login);



}
