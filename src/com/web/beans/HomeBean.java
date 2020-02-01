package com.web.beans;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;

import com.database.entities.NewsLetter;
import com.database.managers.JpaUtil;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
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
}
