package com.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.utils.EmailSender;
import com.web.Session;

/**
 * This bean was made for Become a Member page
 * 
 * @author Francisco
 *
 */

@ManagedBean
@RequestScoped
public class BecameMemberBean {

	private String name;
	private String aboutYourself;
	private String errorMessage;

	/**
	 * This function is called when the user clicks the button submit
	 * 
	 * @return
	 */
	public String submit() {

		if (!name.isBlank() && !aboutYourself.isBlank()) {

			String content = "Eu " + name + ", quero-me juntar á ACM" + "em seguida, está algo acerca de mim:\n"
					+ aboutYourself;

			EmailSender.getInstance().sendTextEmail(Session.getInstance().getUser().getEmail(), "Want to join acm",
					content);
			return "home";
		} else {
			return "";
		}

	}

	/**
	 * Redirects to login page
	 */
	public void redirectToLogin() {
		Session.getInstance().redirectWithContext("/login");
	}
	
	/**
	 * Redirects to login page
	 */
	public void redirectToDefinitions() {
		Session.getInstance().redirectWithContext("/user");
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the aboutYouself
	 */
	public String getAboutYourself() {
		return aboutYourself;
	}

	/**
	 * @param aboutYouself the aboutYouself to set
	 */
	public void setAboutYourself(String aboutYouself) {
		this.aboutYourself = aboutYouself;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}