package com.web.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;

import com.database.entities.User;
import com.database.managers.JpaUtil;
import com.database.managers.UserManager;
import com.web.Session;
import com.web.containers.ProjectContainer;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class UserBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;
	private String firstName;
	private String lastName;
	private String cellPhone;
	private String course;
	private String username;
	private String newPassword;
	private String password;

	private UserContainer user;

	private String rendered = "definitions";

	private String definitionsErrorMessage;

	
	public UserBean() {
		user = Session.getInstance().getUser();
		if(user == null)
			Session.getInstance().redirectToLogin("user");
	}


	/**
	 * If there is an valid userID in request map it returns the correspondent
	 * UserContainer object otherwise returns null
	 */
	public UserContainer getUserPerfil() {
		UserContainer perfil = null;
		String id = Session.getInstance().getRequestMap().get("userID");
		if(id != null && !id.isBlank()) {
			User u = UserManager.getUserById(Integer.parseInt(id));
			if(u != null)
				perfil = new UserContainer(u);
		}

		return perfil;
	}


	/**
	 * Saves changes and commits them to data base.
	 */
	public String saveChanges() {
		User userUpdate = UserManager.getUserById(user.getId());

		if(userUpdate.getPassword().equals(password)) {

			EntityManager em = JpaUtil.getEntityManager();

			em.getTransaction().begin();

			if(email != null && !email.isBlank()) 				//Sets user email
				userUpdate.setEmail(email);

			if(firstName != null && !firstName.isBlank())  		//Sets user first name
				userUpdate.setFristName(firstName);

			if(lastName != null && !lastName.isBlank())  		//Sets user last name
				userUpdate.setLastName(lastName);

			if(cellPhone != null && !cellPhone.isBlank())  		//Sets user cell phone
				userUpdate.setCellPhone(cellPhone);

			if(course != null && !course.isBlank()) 			//Sets user course
				userUpdate.setCourse(course);

			if(username != null && !username.isBlank())  		//Sets user username
				userUpdate.setUsername(username);

			if(newPassword != null && !newPassword.isBlank())  	//Sets user newPassword
				userUpdate.setPassword(newPassword);

			em.merge(userUpdate);
			em.flush();
			em.getTransaction().commit();

			user = new UserContainer(userUpdate);
			Session.getInstance().setUser(user);					//commits changes

			clearForm();
		}else {
			setDefinitionsErrorMessage("Password is incorrect");
		}
		return "";
	}
	
	
	/**
	 * This method cleans the values that remain in the user form
	 */
	private void clearForm() {
		this.email = null;
		this.cellPhone = null;
		this.course = null;
		this.firstName = null;
		this.lastName = null;;
		this.newPassword = null;
		this.password = null;
		this.username = null;
	}
}
