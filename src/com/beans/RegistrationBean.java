package com.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.database.entities.User;
import com.database.managers.UserManager;
import com.utils.EmailSender;


/**
 *This bean must be used in registration forms
 *This bean is used in registration.xhtml
 * 
 * @author RuiMenoita
 */



@ManagedBean
@RequestScoped
public class RegistrationBean {

	private String fristName = "";
	private String lastName = "";
	private String username = "";
	private String password = "";
	private String email = "";
	private String repeatPassword = "";
	private String acceptedTermsAndConditions = "";

	private String errorMessage;







	public String signUp() {
		
		if( !password.equals(repeatPassword)) {	//check passwords
			setErrorMessage("Passwords don't match");
			return "";

		}else if(username.isBlank() || UserManager.getUserByUsername(username) != null){		//check username
			setErrorMessage("Username already exists");
			return "";
			
		}else if(email.isBlank() || UserManager.getUserByEmail(email) != null){				//check email
			setErrorMessage("Email already registed");
			return"";

		}else if(acceptedTermsAndConditions.isBlank() || acceptedTermsAndConditions.equals("false")){	//check terms										//check accepted terms
			setErrorMessage("Terms must be accepted");
			return"";
		
		}else if(!fristName.isBlank() && !lastName.isBlank()) {	

			try {
				User u = UserManager.createUser(email, password, fristName, lastName, username);
				if(u == null)
					setErrorMessage("User already Exits");
				else {
					EmailSender.getInstance().sendActivationMail(u.getEmail(),u.getActivationKey());	//send email
					return "home";
				}
			}catch(Exception e) {
				e.printStackTrace();
				setErrorMessage("Something went wrong");
				return "";
			}

		}else {
			setErrorMessage("Something went wrong");
			return "";
		}
		return "";
	}











	/**
	 * @return the fristName
	 */
	public String getFristName() {
		return fristName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @return the repeatPassword
	 */
	public String getRepeatPassword() {
		return repeatPassword;
	}


	/**
	 * @return the acceptedTermsAndConditions
	 */
	public String getAcceptedTermsAndConditions() {
		return acceptedTermsAndConditions;
	}


	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}











	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}











	/**
	 * @param fristName the fristName to set
	 */
	public void setFristName(String fristName) {
		this.fristName = fristName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @param repeatPassword the repeatPassword to set
	 */
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}


	/**
	 * @param acceptedTermsAndConditions the acceptedTermsAndConditions to set
	 */
	public void setAcceptedTermsAndConditions(String acceptedTermsAndConditions) {
		this.acceptedTermsAndConditions = acceptedTermsAndConditions;
	}


	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
