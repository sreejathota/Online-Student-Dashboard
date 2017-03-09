package com.java.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.constraints.Size;
public class Student {
	@Autowired
private int studentId;
	@NotEmpty@Size(min=4, max=10) 
private String firstName;
	@NotEmpty@Size(min=4, max=10) 
private String lastName;
	 @NotEmpty @Email
private String mailId;
	//@NotEmpty @Length(min=6, max=8) 
private String password;

public Student() {
	
}
public Student(int studentId, String firstName, String lastName, String mailId,
		String password) {
	super();
	this.studentId = studentId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.mailId = mailId;
	this.password = password;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public int getStudentId() {
	return studentId;
}
public void setStudentId(int studentId) {
	this.studentId = studentId;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getMailId() {
	return mailId;
}
public void setMailId(String mailId) {
	this.mailId = mailId;
}

}
