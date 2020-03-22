package com.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;

import com.database.entities.User;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.database.managers.ResearchManager;
import com.database.managers.UserManager;
import com.utils.FileManager;
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

	private String rendered = "settings";



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
		Part img = (Part) e.getNewValue();
		if(FileManager.validImage(img.getContentType())) {
			String path = FileManager.saveFile(img, "users"); //saves files
//			FileManager.deleteFile(user.getImagePath());	  //deletes old photo
			path = path.substring(path.lastIndexOf("users/"));	  // sets path for tomcat
			user.setImagePath(path);						  
			UserManager.updateUser(user.getId(),user);		  //saves user
		}
	}







	/**
	 *Dislikes project 
	 */
	public void dislikeResearch(ActionEvent event) {
		int researchId = (int) event.getComponent().getAttributes().get("researchId");
		ResearchManager.dislike(researchId, Session.getInstance().getUser().getId());
		Session.getInstance().getUser().refresh();
	}






	/**
	 *Removes user from participating in the given project
	 *if user is the project manager it won't let remove the user
	 */
	public void removeProject(ActionEvent event) {
		int projectId = (int) event.getComponent().getAttributes().get("projectId");
		ProjectManager.removeMember(user.getId(),projectId);
	}






	/**
	 *Removes user from participating in the given project
	 *if user is the project manager it won't let remove the user
	 */
	public void removeResearch(ActionEvent event) {
		int projectId = (int) event.getComponent().getAttributes().get("researchId");
		ResearchManager.removeMember(user.getId(),projectId);
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
