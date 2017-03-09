package com.java.bean;


import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	@Autowired

	@NotEmpty @Length(min=6, max=10)
	private String oldPassword;
	@NotEmpty @Length(min=6, max=10)
	private String password;
	@NotEmpty @Length(min=6, max=10)
	private String confirmPassword;
	private String securityQuestion1;
	@NotEmpty @Length(min=6, max=10)
	private String securityAnswer1;
	private String securityQuestion2;
	@NotEmpty @Length(min=6, max=10)
	private String securityAnswer2;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getSecurityQuestion1() {
		return securityQuestion1;
	}
	public void setSecurityQuestion1(String securityQuestion1) {
		this.securityQuestion1 = securityQuestion1;
	}
	public String getSecurityAnswer1() {
		return securityAnswer1;
	}
	public void setSecurityAnswer1(String securityAnswer1) {
		this.securityAnswer1 = securityAnswer1;
	}
	public String getSecurityQuestion2() {
		return securityQuestion2;
	}
	public void setSecurityQuestion2(String securityQuestion2) {
		this.securityQuestion2 = securityQuestion2;
	}
	public String getSecurityAnswer2() {
		return securityAnswer2;
	}
	public void setSecurityAnswer2(String securityAnswer2) {
		this.securityAnswer2 = securityAnswer2;
	}
	
}
