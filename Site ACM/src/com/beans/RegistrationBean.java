package com.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *This bean must be used in registration forms
 *This bean is used in registration.xhtml
 * 
 * @author RuiMenoita
 */



@ManagedBean
@RequestScoped
public class RegistrationBean {

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
//		if(!password.equals(repeatPassword)) {							//Verifies if passwords match
//			setErrorMessage("Passwords aren't equals");
//			return "";
//		}
//
//		if(fristName != null && lastName  != null && password  != null && email != null && repeatPassword  != null ) //verifies if nothing is null
//			try {
//				if(!dataBase.userExist(email))
//					dataBase.signInQuery(email, password, fristName, lastName);
//				else
//					setErrorMessage("Email already registed");
//			} catch (SQLException e) {
//				setErrorMessage("Something went wrong");
//				e.printStackTrace();
//				return "";
//			}

		return "home";
	}




	//Setters
	
	
	
	/**
	 * To set first name need to pass this validators:
	 * Validators:
	 * - Length : ]1,60]
	 * - Regex : "[a-zA-Z]*"
	 * 		--It can only contain letters
	 */
	public void setFristName(String fristName) {
		if(fristName.matches("[a-zA-Z]*") && fristName.length()>1 && fristName.length()<60)
			this.fristName = fristName;
	}
	
	
	
	
	/**
	 * To set first name need to pass this validators:
	 * Validators:
	 * - Length : ]1,60]
	 * - Regex : "[a-zA-Z]*"
	 * 		--It can only contain letters
	 */
	public void setLastName(String lastName) {
		if(lastName.matches("[a-zA-Z]*") && lastName.length()>1 && lastName.length()<=60)
			this.lastName = lastName;
	}
	
	
	
	
	/**
	 * To set first name need to pass this validators:
	 * Validators:
	 * - Length : [8,64]
	 */
	public void setPassword(String password) {
		if(password.length()>=8 && password.length()<=64)
			this.password = password;
	}
	
	
	
	
	/**
	 * To set first name need to pass this validators:
	 * Validators:
	 * - Length : [3,60]
	 */
	public void setCourse(String course) {
		if(course.length()>=3 && course.length()<=60)
			this.course = course;
	}
	
	

	
	/**
	 * To set first name need to pass this validators:
	 * Validators:
	 * - Length : [6,70]
	 * - Regex : "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$"
	 * 		--e.g.: a5sd6@email.pt
	 */
	public void setEmail(String email) {
		if(fristName.matches("[a-zA-Z]*") && fristName.length()>=6 && fristName.length()<=70)
			this.email = email;
	}
	
	
	
	
	/**
	 * if @param repeatPassword is not equals to password it displays an message error
	 */
	public void setRepeatPassword(String repeatPassword) {
			this.repeatPassword = repeatPassword;
			setErrorMessage("Passwords don't match");
	}

	
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	//Getters 

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
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public String getEmail() {
		return email;
	}
}
