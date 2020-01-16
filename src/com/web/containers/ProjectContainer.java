package com.web.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.database.entities.Material;
import com.database.entities.Project;
import com.database.entities.State;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;

import lombok.Data;
import lombok.ToString.Exclude;

/**
 * This object represents the Data transfer Object of class Project (Project DTO)
 * @author RuiMenoita
 */

@Data
public class ProjectContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long likes;
	private int views;
	private int id;
	private int maxMembers;
	private String title;
	private Date deadline;
	private Date subscriptionDeadline;
	private State state ;
	private List<String> tags = new ArrayList<>();
	
	@Exclude
	private String description;
	
	@Exclude
	private String shortDescription;
	
	@Exclude
	private String requirements;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<UserContainer> participants;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<String> imagePath = new ArrayList<>();
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private UserContainer manager;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<Material> material;

	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<ProjectCandidateContainer> candidates;
	
	
	
	
	
	
	/**
	 * Constructor
	 * @param p project to be used to create an instance of ProjectContainer
	 */
	public ProjectContainer(Project p) {
		this.id = p.getId();
		this.maxMembers = p.getMaxMembers();
		this.title  = p.getTitle();
		this.description  = p.getDescription();
		this.shortDescription = p.getShortDescription();
		this.requirements = p.getRequirements();
		this.deadline  = p.getDeadLine();
		this.subscriptionDeadline  = p.getSubscriptionDeadline();
		this.state   = p.getState();
		this.tags  = new ArrayList<>(p.getTags());
		this.imagePath  = new ArrayList<>(p.getImagePath());
		this.likes = JpaUtil.executeQuery("Select count(*) from AcmLike l where l.project.id = "+id,Long.class).get(0);
		this.material = p.getMaterial();
		this.views = p.getViews().size();
	}



	
	
	/**
	 * Constructor
	 * @param id project id to be used to create an instance of ProjectContainer
	 */
	public ProjectContainer(int id) {
		Project p = ProjectManager.findById(id);
		this.id = p.getId();
		this.maxMembers = p.getMaxMembers();
		this.title  = p.getTitle();
		this.description  = p.getDescription();
		this.shortDescription = p.getShortDescription();
		this.requirements = p.getRequirements();
		this.deadline  = p.getDeadLine();
		this.subscriptionDeadline  = p.getSubscriptionDeadline();
		this.state   = p.getState();
		this.tags  = new ArrayList<>(p.getTags());
		this.imagePath  = new ArrayList<>(p.getImagePath());
		this.likes = JpaUtil.executeQuery("Select count(*) from AcmLike l where l.project.id = "+id,Long.class).get(0);
		this.material = p.getMaterial();
		this.views = p.getViews().size();
	}




	/**
	 * 
	 * @param user
	 */
	public ProjectContainer(UserContainer user) {
		this.manager = user;
		this.participants = new ArrayList<>();
		this.participants.add(user);
		this.material = new ArrayList<>();
	}

	
	
	
	
	/**
	 * @return returns project manager
	 */
	public UserContainer getManager() {
		if(manager == null) 
			manager = new UserContainer(ProjectManager.findById(id).getManager());
		return manager;
	}


	/**
	 * @return returns project candidate
	 */
	public List<ProjectCandidateContainer> getCandidates() {
		if(candidates == null) 
			candidates = ProjectManager.getCandidates(id).stream().map(ProjectCandidateContainer::new).collect(Collectors.toList());
		return candidates;
	}

	
	
	/**
	 * @return returns project participants
	 */
	public List<UserContainer> getParticipants() {
		if(participants == null) 
			participants = ProjectManager.findById(id).getParticipants().stream().map(UserContainer::new).collect(Collectors.toList());
		return participants;
	}

	
	
	
	/**
	 * refresh's project
	 */
	public void refresh() {
		Project p = ProjectManager.findById(id);
		this.id = p.getId();
		this.maxMembers = p.getMaxMembers();
		this.title  = p.getTitle();
		this.description  = p.getDescription();
		this.shortDescription = p.getShortDescription();
		this.requirements = p.getRequirements();
		this.deadline  = p.getDeadLine();
		this.subscriptionDeadline  = p.getSubscriptionDeadline();
		this.state   = p.getState();
		this.tags  = new ArrayList<>(p.getTags());
		this.imagePath  = new ArrayList<>(p.getImagePath());
		this.likes = JpaUtil.executeQuery("Select count(*) from AcmLike l where l.project.id = "+id,Long.class).get(0);
		this.participants = null;
		this.manager = null;
		this.candidates = null;
		this.material = p.getMaterial();
		this.views = p.getViews().size();
	}
}
