package com.web.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
	private int views;
	private String imagePath;
	private String email;
	private String firstName;
	private String lastName;
	private String course;
	private String cellPhone;
	private String username;
	private String about;
	private Date lastLog;
	private boolean isMember;
	private boolean isAdmin;
	private boolean isActive;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<ProjectContainer> joinedProjects;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<EventContainer> joinedEvents;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<ResearchContainer> joinedResearches;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<ProjectContainer> likedProjects;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<EventContainer> likedEvents;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<ResearchContainer> likedResearches;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<CandidateContainer> researchesCandidatures;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<CandidateContainer> projectsCandidatures;
	
	
	
	
	public UserContainer(int id) {
		User user = UserManager.getUserById(id);
		this.id = user.getId();
		this.views = user.getViews().size();
		this.imagePath = user.getImagePath();
		this.email = user.getEmail();
		this.firstName = user.getFristName();
		this.lastName = user.getLastName();
		this.course = user.getCourse();
		this.cellPhone = user.getCellPhone();
		this.username = user.getUsername();
		this.isMember = user.isMember();
		this.isAdmin = user.isAdmin();
		this.lastLog = user.getLogs().get(user.getLogs().size()-1);
		this.isActive = user.isActive();
		this.about = user.getAbout();
	}


	
	
	
	public UserContainer(User user) {
		this.id = user.getId();
		this.views = user.getViews().size();
		this.imagePath = user.getImagePath();
		this.email = user.getEmail();
		this.firstName = user.getFristName();
		this.lastName = user.getLastName();
		this.course = user.getCourse();
		this.cellPhone = user.getCellPhone();
		this.username = user.getUsername();
		this.isMember = user.isMember();
		this.isAdmin = user.isAdmin();
		this.lastLog = user.getLogs().get(user.getLogs().size()-1);
		this.isActive = user.isActive();
		this.about = user.getAbout();
	}


	
	
	
	/**
	 * @return return a list with projects that user is a participant
	 */
	public List<ProjectContainer> getJoinedProjects(){
		if(joinedProjects == null)
			this.joinedProjects = UserManager.getJoinedProject(id).stream().map(ProjectContainer::new).collect(Collectors.toList());
		return joinedProjects;
	}


	
	
	
	/**
	 * @return return a list with projects that user is a participant
	 */
	public List<EventContainer> getJoinedEvents(){
		if(joinedEvents == null)
			this.joinedEvents = UserManager.getJoinedEvents(id).stream().map(EventContainer::new).collect(Collectors.toList());
		return joinedEvents;
	}


	
	
	
	/**
	 * @return return a list with projects that user is a participant
	 */
	public List<ResearchContainer> getJoinedResearches(){
		if(joinedResearches == null)
			this.joinedResearches = UserManager.getLikesResearches(id).stream().map(ResearchContainer::new).collect(Collectors.toList());
		return joinedResearches;
	}


	
	
	/**
	 * @return 
	 */
	public List<ProjectContainer> getLikedProjects(){
		if(likedProjects == null) 
			this.likedProjects = ProjectManager.getLikedProjects(id).stream().map(ProjectContainer::new).collect(Collectors.toList());
		return likedProjects;
	}


	
	/**
	 * 
	 * @return
	 */
	public List<EventContainer> getLikedEvents(){
		if(likedEvents == null) 
			this.likedEvents = UserManager.getLikedEvents(id).stream().map(EventContainer::new).collect(Collectors.toList());
		return likedEvents;
	}


	
	
	
	/**
	 * 
	 */
	public List<ResearchContainer> getLikedResearches(){
		if(likedResearches == null) 
			this.likedResearches = UserManager.getLikesResearches(id).stream().map(ResearchContainer::new).collect(Collectors.toList());
		return likedResearches;
	}


	
	
	
	/**
	 * 
	 */
	public List<CandidateContainer> getResearchesCandidatures(){
		if(researchesCandidatures == null) 
			this.researchesCandidatures = UserManager.getResearchesCandidatures(id).stream().map(CandidateContainer::new).collect(Collectors.toList());
		return researchesCandidatures;
	}


	
	
	
	/**
	 * 
	 */
	public List<CandidateContainer> getProjectsCandidatures(){
		if(projectsCandidatures == null) 
			this.projectsCandidatures = UserManager.getProjectsCandidatures(id).stream().map(CandidateContainer::new).collect(Collectors.toList());
		return projectsCandidatures;
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
		this.views = user.getViews().size();
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
		this.about = user.getAbout();
		this.likedProjects = null;
		this.likedEvents = null;
		this.likedResearches = null;
		this.joinedProjects = null;
		this.joinedEvents = null;
		this.joinedResearches = null;
		this.researchesCandidatures = null;
		this.projectsCandidatures = null;
	}
}
