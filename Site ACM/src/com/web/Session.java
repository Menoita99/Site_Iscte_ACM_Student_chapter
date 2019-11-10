package com.web;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.conatiners.objects.UserContainer;

/**
 *This class represents a session 
 * 
 * @author RuiMenoita
 */
public class Session {

	private static Session INSTANCE;


	public static Session getInstance() {
		if(INSTANCE == null) INSTANCE = new Session();
		return INSTANCE;
	}

	/**
	 * This method returns a ExternalContext object through the current HTTP request 
	 * and this is only possible if a request was shot. 
	 * This means you can not call this method out of an HTTP request
	 *
	 * @throws new RuntimeException
	 */
	private ExternalContext currentExternalContext(){ 
		if (FacesContext.getCurrentInstance() == null)
			throw new RuntimeException("FacesContext can’t be called outside of a HTTP request");
		else 
			return FacesContext.getCurrentInstance().getExternalContext(); 
	}

	/**
	 * returns the sessionMap that contains the attributes 
	 */
	public Map<String,Object> getSessionMap(){
		return currentExternalContext().getSessionMap();
	}
	
	/**
	 * Clear all the attributes on sessionMap and closes the session
	 * 
	 * There are two ways of closing a Session, or timeout declared on web.xml or calling this method.
	 */
	public void logout() {
		currentExternalContext().invalidateSession();
	}
	
	
	/**
	 * This method set's a new attribute on current Session.
	 * 
	 * This method is useful to store information throw site navigation
	 * e.g: user id 
	 */
	public void setSessionAtribute(String key,Object value) {
		currentExternalContext().getSessionMap().put(key, value);
	}
	
	
	/**
	 * Method used to get information stored on Session
	 */
	public Object getSessionAtribute(String key) {
		return currentExternalContext().getSessionMap().get(key);
	}
	
	
	
	/**
	 * Set's the session attribute user
	 */
	public void setUser(UserContainer userContainer) {
		setSessionAtribute("user", userContainer);
	}
	
	
	/**
	 * return the session attribute user
	 */
	public UserContainer getUser() {
		return (UserContainer) getSessionAtribute("user");
	}

	
	
	/**
	 * Redirects to @param url
	 */
	public void redirect(String url) {
		try {
			currentExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
