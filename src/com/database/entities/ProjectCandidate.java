package com.database.entities;

import com.database.entities.User;
import com.database.managers.ProjectManager;
import com.database.managers.UserManager;
import com.web.containers.ProjectCandidateContainer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: ProjectCandidate
 *
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "project_candidate")
public class ProjectCandidate implements Serializable {

	private static final long serialVersionUID = 1L;   
	
	@EmbeddedId
	private ProjectCandidateID id = new ProjectCandidateID(); 
	
	@Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@MapsId("userID")
	private User user;
	
	@Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@MapsId("projectID")
	private Project project;
	
	@Column(length = 665, nullable = false)
	private String motivation;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date(System.currentTimeMillis());
	
	
	
	/**
	 * 
	 */
	public ProjectCandidate(ProjectCandidateContainer candidature) {
		this.user = UserManager.getUserById(candidature.getUser().getId());
		this.project = ProjectManager.findById(candidature.getProject().getId());
		this.motivation = candidature.getText();
	}



	/**
	 * 
	 */
	public ProjectCandidate(User user, Project project, String motivation) {
		this.user = user;
		this.project = project;
		this.motivation = motivation;
	}



	/**
	 * 
	 */
	public ProjectCandidate(User user, Project project) {
		this.user = user;
		this.project = project;
	}
}
