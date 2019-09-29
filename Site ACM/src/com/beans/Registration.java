package com.beans;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.utils.DataBaseConnection;

@ManagedBean
@RequestScoped
public class Registration {

	private String fristName;
	private String lastName;
	private String password;
	private String course;
	private String email;
	private String repeatPassword;

	private String errorMessage;
	
	
	
	/**
	 * Verifies and insert an user in DataBase 
	 */
	public String signUp() {
		if(!password.equals(repeatPassword)) {							//Verifies if passwords match
			setErrorMessage("Passwords don't match");
			return "";
		}
		
		if(fristName != null && lastName  != null && password  != null && email != null && repeatPassword  != null ) //verifies if nothing is null
			try {
				if(!DataBaseConnection.getInstance().userExist(email))
					DataBaseConnection.getInstance().signInQuery(email, password, fristName, lastName);
				else
					setErrorMessage("Email already registed");
			} catch (SQLException e) {
				setErrorMessage("Something went wrong");
				e.printStackTrace();
				return "";
			}
			
		return "landpage";
	}

	
	public String teste() {
		System.out.println("Chamei home em landpage");
		return "landpage";
	}

	//Getters and setters

	public String getFristName() {
		return fristName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPassword() {
		return password;
	}
	public String getCourse() {
		return course;
	}
	public void setFristName(String fristName) {
		this.fristName = fristName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPassword(String passWord) {
		this.password = passWord;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
