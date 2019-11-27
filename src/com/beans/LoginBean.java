package com.beans;


import com.containers.objects.UserContainer;
import com.database.entities.User;
import com.database.managers.UserManager;
import com.web.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;



/**
 *This bean must be used in login forms
 *This bean is used in login.xhtml
 * 
 * @author RuiMenoita
 */

@ManagedBean
@RequestScoped
public class LoginBean {

	private String emailOrUsename;
	private String password;
	private String error;				


	/**
	 * This method validates email and password.
	 * if it validates with success it stores UserContainer in session and redirects
	 * or to land page or to the last page that user was.
	 * otherwise displays an error message
	 */
	public String login() {
		if(emailOrUsename != null && password!=null) {
			
			User user = UserManager.emailLogin(emailOrUsename, password);
			if(user ==  null)
				user = UserManager.usernameLogin(emailOrUsename, password);

			if( user!= null && user.isActive()) {			
				setError("");
				Session.getInstance().setUser(new UserContainer(user));		// stores user in session

				return "home";												//Navigation rule the redirects user to home page
			}else if(user != null && !user.isActive()) {					//checks if user is active
				return "activate";
			}else 	
				setError("Username or password are incorrect");				//Displays an error message on template
		}else {
			setError("Empty fields");
		}

		return "";															//Stays in the same page
	}


	/**
	 * Ends user session and cleans the session map
	 */
	public String logout() {
		Session.getInstance().logout();
		return "home";		
	}


	/**
	 * @return the emailOrUsename
	 */
	public String getEmailOrUsename() {
		return emailOrUsename;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}


	/**
	 * @param emailOrUsename the emailOrUsename to set
	 */
	public void setEmailOrUsename(String emailOrUsename) {
		this.emailOrUsename = emailOrUsename;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}


}
