package com.java.bean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Login {
	
	//@NotNull
	//@NumberFormat(style= Style.NUMBER)
	//@Length(max=9,min=9)
	private int id;
	@NotEmpty @Length(min=6, max=10)
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Login(int id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public Login() {
		
	}
	
}
