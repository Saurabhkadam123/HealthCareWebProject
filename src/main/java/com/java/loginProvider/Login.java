package com.java.loginProvider;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.java.project.Provider;
import com.java.project.Status;

import javax.persistence.Column;

@Entity
@Table(name = "login")
@ManagedBean(name = "login")
@SessionScoped
public class Login {

	@Id
	@GeneratedValue
	@Column(name = "userId")
	private int userId;
	
	@Column(name = "password")
	private String password;


	@Column(name = "flag")
	private String flag;



	@Column(name = "Otp")
	private String Otp;

	@Column(name = "Otpsendtime")
	private Date otpSendTime;

	@Column(name = "Otpexpiretime")
	private Date otpExpiryTime;
	
	@Column(name = "pEmailId")
	private String providerEmailId;
	
	@Column(name="username")
	private String username;
	
	
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProviderEmailId() {
		return providerEmailId;
	}

	public void setProviderEmailId(String providerEmailId) {
		this.providerEmailId = providerEmailId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOtp() {
		return Otp;
	}

	public void setOtp(String otp) {
		Otp = otp;
	}

	public Date getOtpSendTime() {
		return otpSendTime;
	}

	public void setOtpSendTime(Date otpSendTime) {
		this.otpSendTime = otpSendTime;
	}

	public Date getOtpExpiryTime() {
		return otpExpiryTime;
	}

	public void setOtpExpiryTime(Date otpExpiryTime) {
		this.otpExpiryTime = otpExpiryTime;
	}

	@Override
	public String toString() {
		return "Login [userId=" + userId + ", password=" + password + ", flag=" + flag + ", Otp=" + Otp
				+ ", otpSendTime=" + otpSendTime + ", otpExpiryTime=" + otpExpiryTime + ", providerEmailId="
				+ providerEmailId + ", username=" + username + "]";
	}
	

}
