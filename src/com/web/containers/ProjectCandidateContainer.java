package com.web.containers;

import java.io.Serializable;
import java.util.Date;

import com.database.entities.ProjectCandidate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectCandidateContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UserContainer user;
	private ProjectContainer project;
	private String text;
	private Date date;
	
	
	/**
	 * Constructor
	 */
	public ProjectCandidateContainer(ProjectCandidate p) {
		this.user = new UserContainer(p.getUser());
		this.project = new ProjectContainer(p.getProject());
		this.text = p.getMotivation();
		this.date = p.getDate();
	}
	
	
	
	
	@Override
	public String toString() {
		String u = user == null ? "null": "[id=" + user.getId() + " username="+ user.getUsername()+"] , ";
		String p = project == null ? "null" : " [id=" + project.getId()  + " title="+ project.getTitle()+"]";
		
		return "ProjectCandidateContainer( user: "+ u +"project: "+ p+ ", Text: " + text +" )";
	}
}
