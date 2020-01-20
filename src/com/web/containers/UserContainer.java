package com.web.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.database.entities.User;
import com.database.managers.ProjectManager;
import com.database.managers.UserManager;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

/**
 *	This class represents an user that can be stored in session 
 *	This must be used only to save and read information
 *
 * @author RuiMenoita
 */
@Data
@NoArgsConstructor
public class UserContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id; 		
	private String imagePath;
	private String email;
	private String firstName;
	private String lastName;
	private String course;
	private String cellPhone;
	private String username;
	private boolean isMember;
	private boolean isAdmin;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<ProjectContainer> joinedProjects;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<ProjectContainer> likedProjects;
	
	
	
	
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
		this.isMember = user.isMember();
		this.isAdmin = user.isAdmin();
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
		this.isMember = user.isMember();
		this.isAdmin = user.isAdmin();
	}


	
	
	
	/**
	 * @return return a list with projects that user is a participant
	 */
	public List<ProjectContainer> getJoinedProjects(){
//		if(joinedProjects == null)
//			this.joinedProjects = UserManager.getUserById(id).getProjects().stream().map(ProjectContainer::new).collect(Collectors.toList());
		return joinedProjects;
	}


	
	
	public List<ProjectContainer> getLikedProjects(){
		if(likedProjects == null) 
			this.likedProjects = ProjectManager.getLikedProjects(id).stream().map(ProjectContainer::new).collect(Collectors.toList());
		return likedProjects;
	}

	
	
	
	
	/**
	 * This method returns all the events that an user is in  
	 * only when this method is called will it retrieve information from the database
	 */
	public List<EventContainer> getEvents() {
		return new ArrayList<EventContainer>();
	}




	/**
	 * refresh 
	 */
	public void refresh() {
		User user = UserManager.getUserById(id);
		this.id = user.getId();
		this.imagePath = user.getImagePath();
		this.email = user.getEmail();
		this.firstName = user.getFristName();
		this.lastName = user.getLastName();
		this.course = user.getCourse();
		this.cellPhone = user.getCellPhone();
		this.username = user.getUsername();
		this.isMember = user.isMember();
		this.isAdmin = user.isAdmin();
		this.likedProjects = null;
		this.joinedProjects = null;
	}
}
