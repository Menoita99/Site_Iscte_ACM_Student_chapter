package com.beans;

import javax.faces.bean.SessionScoped;

import jdk.jfr.Name;


@Name("login")
@SessionScoped
public class Login {

	private String email;
	private String password;





	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
