package com.web.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.database.entities.User;
import com.database.managers.UserManager;
import com.utils.EmailSender;
import com.web.Session;

import lombok.Data;


@ManagedBean
@RequestScoped
@Data
public class ActivateBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;
	private FacesContext context = FacesContext.getCurrentInstance();



	/**
	 * Activates user giving the key, and if it has success it redirects user to home page
	 */
	public void activateUser(String key) {
		if(key != null && !key.isBlank()) {
			boolean isActive = UserManager.activateUser(key); 				//activates user
			if(isActive) 
				Session.getInstance().redirectWithContext("/home");
			else 
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Something went wrong, cound not activate user", ""));
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
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Could not find any user with the given email", ""));
			else {
				EmailSender.getInstance().sendActivationMail(u.getEmail(), u.getActivationKey());
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email sent, please verify your email account", ""));

			}
		}
	}
}
