package com.beans;


import com.conatiners.objects.UserContainer;
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

			UserContainer userContainer = UserContainer.convertToUserContainer(UserManager.emailLogin(email, password));

			if( userContainer != null) {			
				setError("");
				Session.getInstance().setUser(userContainer);					// stores user in session

				if(Session.getInstance().getSessionAtribute("lastPage") != null) {										//if the attribute "lastPage" is set (it means filter was worked)
					Session.getInstance().redirect(Session.getInstance().getSessionAtribute("lastPage").toString()); 	//redirects to last page
					return "";										
				}

				return "home";										//Navigation rule the redirects user to home page
			}else
				setError("Username or password are incorrect");		//Displays an error message on template
		}else {
			System.out.println("AQUI ACARALHO");
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
