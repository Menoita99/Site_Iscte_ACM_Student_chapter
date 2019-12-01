package com.web;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.web.containers.UserContainer;

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
	 * returns the requestMap that contains the attributes 
	 */
	public Map<String,String> getRequestMap(){
        return currentExternalContext().getRequestParameterMap();
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
	 * if there isn't the requested object with the key given it returns null
	 */
	public Object getSessionAtribute(String key) {
		return currentExternalContext().getSessionMap().getOrDefault(key, null);
	}
	
	
	
	
	
	
	/**
	 * Set's the session attribute user
	 */
	public void setUser(UserContainer userContainer) {
		setSessionAtribute("user", userContainer);
	}
	
	
	
	
	
	/**
	 * return the session attribute user, 
	 * if there is no user present in session this returns null
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





	/**
	 * Redirect user to a page of this website
	 * e.g:
	 * 		if path = "/home"
	 * 		this page will be redirected to home page
	 * 	
	 * @param path url path
	 */
	public void redirectWithContext(String path) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest origRequest = (HttpServletRequest)context.getExternalContext().getRequest();
		String contextPath = origRequest.getContextPath();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath  + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}





	/**
	 *  Set's the session attribute lastPage
	 */
	public void setLastPage(String lastPage) {
		setSessionAtribute("lastPage", lastPage);
	}





	/**
	 *  Set's the session attribute lastPage
	 */
	public String getLastPage() {
		return (String) getSessionAtribute("lastPage");
	}
}
