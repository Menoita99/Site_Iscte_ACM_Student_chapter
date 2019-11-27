package com.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.database.entities.NewsLetter;
import com.database.managers.JpaUtil;

@ManagedBean
@RequestScoped
public class HomeBean {
	
	private String email;
	
	private String errorMessage;
	
	private String infoMessage;
	
	
	
	
	/**
	 * This method adds the given email to database to send newsletters
	 */
	public String submitEmail() {
		if(email != null && !email.isBlank()) {
			
			List<NewsLetter> result = JpaUtil.executeQuery("SELECT n FROM NewsLetter n WHERE n.email = '"+email+"'",NewsLetter.class);
			
			if(result.isEmpty()) {									//check if email was already submitted
				NewsLetter n = new NewsLetter();				
				n.setEmail(email);
				JpaUtil.createEntity(n);							//create entity
				setInfoMessage("Email submited with success");		//gives info to user
				setErrorMessage(null);
				email = null;
			}else {
				setErrorMessage("Email already submited");
				setInfoMessage(null);
			}
		}
		
		return null;
	}

	
	
	
	
	
	
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	
	
	
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	
	
	
	
	/**
	 * @return the infoMessage
	 */
	public String getInfoMessage() {
		return infoMessage;
	}

	
	
	
	
	/**
	 * @param infoMessage the infoMessage to set
	 */
	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	
	
	
	
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	
	
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
