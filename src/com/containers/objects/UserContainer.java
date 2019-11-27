package com.containers.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.database.entities.Event;
import com.database.entities.User;
import com.database.managers.EventManager;
import com.database.managers.UserManager;

/**
 *	This class represents an user that can be stored in session 
 *	This must be used only to save and read information
 *
 * @author RuiMenoita
 */
public class UserContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id = -1; 		//simulates null id
	private String imagePath;
	private String email;
	private String firstName;
	private String lastName;
	private String course;
	private String cellPhone;
	private String username;

	
	
	
	
	public UserContainer() {}

	
	
	
	
	public UserContainer(int id) {
		User user = UserManager.getUserById(id);
		
		this.id = user.getId();
		this.imagePath = user.getImagePath();
		this.email = user.getEmail();
		this.firstName = user.getFristName();
		this.lastName = user.getLastName();
		this.course = user.getCourse();
		this.cellPhone = user.getCellPhone();
		this.username = user.getUsername();
	}

	
	
	
	
	public UserContainer(User user) {
		this.id = user.getId();
		this.imagePath = user.getImagePath();
		this.email = user.getEmail();
		this.firstName = user.getFristName();
		this.lastName = user.getLastName();
		this.course = user.getCourse();
		this.cellPhone = user.getCellPhone();
		this.username = user.getUsername();
	}

	
	
	
	
	/**
	 * @return the projects
	 */
	public List<ProjectContainer> getProjects() {
		return null;
	}

	
	
	
	
	/**
	 * This method returns all the events that an user is in  
	 * only when this method is called will it retrieve information from the database
	 */
	public List<EventContainer> getEvents() {
		List<EventContainer> events = new ArrayList<>();
		
		for (Event e : EventManager.getParticipations(id)) 
			events.add(new EventContainer(e));
		
		return events;
	}

	
	
	
	
	//----------------------------------
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	
	
	
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
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
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}

	
	
	
	
	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	
	
	
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	
	
	
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	
	
	
	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	 * @param course the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	
	
	
	
	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	
	
	
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
