package com.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.utils.EmailSender;

import lombok.Data;

/**
 * This bean was made for Become a Member page
 * 
 * @author Francisco
 *
 */

@Data
@ManagedBean
@RequestScoped
public class BecameMemberBean {

	private String name;
	private String email;
	private String aboutYourself;
	private String errorMessage;

	public String submit() {
		

		if (!name.isBlank() && !email.isBlank() && !aboutYourself.isBlank()) {

			String content = "Eu " + name + ", quero-me juntar á ACM" + "em seguida, está algo acerca de mim:\n"
					+ aboutYourself;

			EmailSender.getInstance().sendTextEmail("car31dias321@gmail.com", "Want to join acm", content);
			return "home";
		}else {
			return "";
		}

	}
	
	

}