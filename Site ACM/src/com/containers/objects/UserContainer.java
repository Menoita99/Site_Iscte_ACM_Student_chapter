package com.containers.objects;

import java.io.Serializable;
import java.util.List;

import com.database.entities.User;
import com.database.managers.ProjectManager;

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
	private String fristName;
	private String lastName;
	private String course;
	private String cellPhone;
	private String username;

	private List<Integer> projects;

	private List<Integer> events;

	public UserContainer(int id, String imagePath, String email, String fristName, String lastName, String course, String cellPhone, String username) {
		this.id = id;
		this.imagePath = imagePath;
		this.email = email;
		this.fristName = fristName;
		this.lastName = lastName;
		this.course = course;
		this.cellPhone = cellPhone;
		this.username= username;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getEmail() {
		return email;
	}

	public String getFristName() {
		return fristName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCourse() {
		return course;
	}

	public int getId() {
		return id;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the projects
	 */
	public List<Integer> getProjects() {
		return projects;
	}


	/**
	 * @return the events
	 */
	public List<Integer> getEvents() {
		return events;
	}


	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Integer> projects) {
		this.projects = projects;
	}


	/**
	 * @param events the events to set
	 */
	public void setEvents(List<Integer> events) {
		this.events = events;
	}


	/**
	 *This method converts an User to an UserConatiner 
	 */
	public static UserContainer convertToUserContainer(User user) {
		if( user == null)return null;

		int id = user.getId();
		String email = user.getEmail();
		String fristName = user.getFristName();
		String lastName = user.getLastName();
		String imagePath = user.getImagePath();
		String course = user.getCourse();
		String cellPhone = user.getCellPhone();
		String username = user.getUsername();

		UserContainer uc = new UserContainer(id, imagePath, email, fristName, lastName, course, cellPhone, username);

		uc.setProjects(ProjectManager.getUserProjectsIDs(id));

		//uc.setEvents(EventManager.getUserEventsIDs(id));

		return uc;
	}
}
