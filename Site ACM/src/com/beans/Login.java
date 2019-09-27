package com.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.utils.DataBaseConnection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Login {

	private String email;
	private String password;
	private String error;

	/**
	 * if username and password are correct, it open's a user session
	 * otherwise displays a error label
	 */
	public void login() {
		if(email != null && password!=null) {
			if(validateLogin()) {
				System.out.println("LOGADO");
				setError("");
			}else
				setError("Username or password are incorrect");
		}
	}
	
	
	/**
	 * Verifies if the combination of user and password match to an user on DB
	 */
	private boolean validateLogin() {
		try {
			ResultSet rst = DataBaseConnection.getInstance().loginQuery(email,password);
			return rst.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getError() {
		return error;	
	}
	
	public void setError(String error) {
		this.error = error;
	}

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
