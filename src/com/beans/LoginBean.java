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

	private String email;
	private String password;
	private String error;				


	/**
	 * This method validates email and password.
	 * if it validates with success it stores UserContainer in session and redirects
	 * or to land page or to the last page that user was.
	 * otherwise displays an error message
	 */
	public String login() {
		if(email != null && password!=null) {
			
			User user = UserManager.emailLogin(email, password);
			UserContainer userContainer = UserContainer.convertToUserContainer(user);

			if( userContainer != null && user.isActive()) {			
				setError("");
				Session.getInstance().setUser(userContainer);		// stores user in session

				return "home";										//Navigation rule the redirects user to home page
			}else if(!user.isActive()) {							//checks if user is active
				return "activate";
			}else 	
				setError("Username or password are incorrect");		//Displays an error message on template
		}else {
			setError("Empty fields");
		}

		return "";													//Stays in the same page
	}


	/**
	 * Ends user session and cleans the session map
	 */
	public String logout() {
		Session.getInstance().logout();
		return "home";		
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
