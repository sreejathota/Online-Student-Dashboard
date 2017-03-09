package com.java.bean;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

public class Faculty {
	@Autowired
	private int facultyId;
	@NotEmpty@Size(min=4, max=10)
	private String facultyName;
	 @NotEmpty @Email
	private String mailId;
	private String password;
	private List<String> schedules=new ArrayList<String>();
	
	public List<String> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<String> schedules) {
		this.schedules = schedules;
	}
	public int getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
