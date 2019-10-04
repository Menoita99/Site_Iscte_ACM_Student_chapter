package com.beans;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.utils.DataBaseConnection;
import com.web.Session;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
	
@ManagedBean
@RequestScoped
public class Login {

	private String email;
	private String password;
	private String error;				

	/**
	 * if email and password are correct, it open's a user session
	 * otherwise displays a error label
	 */
	public String login() {
		if(email != null && password!=null) {
			int userId =validateLogin();
			
			if( userId != -1) {			
				setError("");
				Session.getInstance().setUserId(userId);
				
				if(Session.getInstance().getSessionAtribute("lastPage") != null) {	//redirects to "lastPage" if the attribute "lastPage" is set
					Session.getInstance().redirect(Session.getInstance().getSessionAtribute("lastPage").toString()); 	
					return "";
				}
				
				return "landpage";										//Navigation rule the redirects user to home page
			}else
				setError("Username or password are incorrect");		//Displays an error message on template
		}
		return "";													//Stays in the same page
	}

	public String map() {
		Session.getInstance().setSessionAtribute("teste", 0);
		for (String s: Session.getInstance().getSessionMap().keySet()) {
			System.out.println(s.toString());
		}
		return "Something";
	}


	/**
	 * Verifies if the combination of email and password match to an user on DB
	 * 
	 * if there isn't an user with the given email and password returns -1
	 * otherwise returns the user id.
	 */
	private int validateLogin() {
		int userId = -1;
		try {
			ResultSet rst = DataBaseConnection.getInstance().loginQuery(email,password);
			
			if( rst.next())										//Verifies if there is a user with the email and password given
				userId = rst.getInt("id_user");		
			
			rst.close();										//Close Result Set
			return userId;
			
		} catch (SQLException e) {e.printStackTrace();}
		
		return userId;
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
