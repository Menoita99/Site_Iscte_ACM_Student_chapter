package com.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.database.entities.User;
import com.database.managers.UserManager;
import com.utils.EmailSender;
import com.web.Session;


@ManagedBean
@RequestScoped
public class ActivateBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String errorMessage;
	
	private String email;


	
	
	/**
	 * Activates user giving the key, and if it has success it redirects user to home page
	 */
	public void activateUser(String key) {
		if(key != null && !key.isBlank()) {
			boolean isActive = UserManager.activateUser(key); 				//activates user
			if(isActive) 
				Session.getInstance().redirectWithContext("/home");
			else 
				setErrorMessage("Something went wrong, cound not activate user");
		}
	}

	
	
	
	
	
	/**
	 * Resends activation email
	 */
	public void resendEmail() {
		if(email != null && !email.isBlank()) {
			User u = UserManager.getUserByEmail(email);
			System.out.println(email);
			if(u == null)
				setErrorMessage("Could not find any user with the given email");
			else {
				EmailSender.getInstance().sendActivationMail(u.getEmail(), u.getActivationKey());
				setErrorMessage("Email sent, please verify your email account");
			}
		}
		
	}
	
	
	
	
	
	
	

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	
	
	
	
	
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}






	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}






	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
