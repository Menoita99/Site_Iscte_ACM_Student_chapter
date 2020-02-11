package com.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.database.entities.User;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.database.managers.UserManager;
import com.web.Session;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class UserBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private UserContainer user;
	private UserContainer update = new UserContainer();
	
	private String username;
	private String email;
	private String password;
	private String newPassword;
	
	private String rendered = "definitions";



	@PostConstruct
	public void init() {
		user = Session.getInstance().getUser();
		if(user == null) 
			Session.getInstance().redirectToLogin("user"); 	//redirect to login
		else 
			user.refresh();
	}

	
	
	
	/**
	 * verify if username already exist
	 */
	public void setUsername(String username) {
		if(UserManager.getUserByUsername(username)!= null) 
			sendMessageToComponent(":definitions:def_form:username","Username already exist");
		else 
			update.setUsername(username);	
	}

	
	
	
	/**
	 * verify if email already exist
	 */
	public void setEmail(String email) {
		if(UserManager.getUserByUsername(email)!= null) 
			sendMessageToComponent(":definitions:def_form:email","Email already exist");
		else 
			update.setEmail(email);	
	}

	
	
	
	/**
	 * Updates user 
	 */
	public void saveChanges(ActionEvent e) {
		User u = UserManager.emailLogin(user.getEmail(), password);
		if(u != null) {
			if(newPassword != null && !newPassword.isEmpty())
				u.setPassword(newPassword);
			u.update(update);
			JpaUtil.mergeEntity(u);
			user.refresh();
			update = new UserContainer(); //cleans form
		}else
			sendMessageToComponent(":def_form:password","Password is incorrect");
		
	}
	
	
	

	
	
	/**
	 *Dislikes project 
	 */
	public void dislikeProject(ActionEvent event) {
		int projectId = (int) event.getComponent().getAttributes().get("projectId");
		ProjectManager.dislike(projectId, Session.getInstance().getUser().getId());
		Session.getInstance().getUser().refresh();
	}
	
	
	

	
	/**
	 * 
	 * @param e
	 */
	public void changePerfilImage(ValueChangeEvent e ) {
		//TODO
	}
	
	
	
	
	
	
	
	/**
	 *Dislikes project 
	 */
	public void dislikeResearch(ActionEvent event) {
		//TODO
	}
	
	
	

	
	
	/**
	 *Dislikes project 
	 */
	public void dislikeLike(ActionEvent event) {
		//TODO
	}
	
	
	
	
	
	/**
	 * Sends a message to component
	 * 
	 * @param componentId
	 * @param message
	 */
	private void sendMessageToComponent(String componentId, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(componentId, msg);
	}
}
