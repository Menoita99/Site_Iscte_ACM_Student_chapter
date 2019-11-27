package com.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

import com.containers.objects.UserContainer;
import com.database.entities.User;
import com.database.managers.JpaUtil;
import com.database.managers.UserManager;
import com.web.Session;

@ManagedBean
@RequestScoped
public class UserBean {

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

	
	
	
	
	/**
	 * If there is an valid userID in request map it returns the correspondent
	 * UserContainer object otherwise returns null
	 */
	public UserContainer getPerfilUser() {
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








	/**
	 * Redirects to login page
	 */
	public void redirectToLogin() {
		Session.getInstance().redirectWithContext("/login");
	}
	


	
	
	
	
	/**
	 * @return the rendered
	 */
	public String getRendered() {
		return rendered;
	}

	/**
	 * @return the user
	 */
	public UserContainer getUser() {
		user = Session.getInstance().getUser();
		return user;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}



	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}



	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}



	/**
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}



	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}



	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	
	/**
	 * @return the definitionsErrorMessage
	 */
	public String getDefinitionsErrorMessage() {
		return definitionsErrorMessage;
	}








	/**
	 * @param definitionsErrorMessage the definitionsErrorMessage to set
	 */
	public void setDefinitionsErrorMessage(String definitionsErrorMessage) {
		this.definitionsErrorMessage = definitionsErrorMessage;
	}








	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}



	/**
	 * @param course the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}



	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}



	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(UserContainer user) {
		this.user = user;
	}

	/**
	 * @param rendered the rendered to set
	 */
	public void setRendered(String rendered) {
		Session.getInstance().setLastPage("/user");
		this.rendered = rendered;
	}
}
