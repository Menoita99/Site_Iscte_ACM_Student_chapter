package com.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.database.entities.Candidate;
import com.database.entities.Project;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.web.Session;
import com.web.containers.CandidateContainer;
import com.web.containers.ProjectContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class ProjectBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private ProjectContainer project = null;
	private CandidateContainer candidature;



	@PostConstruct
	public void init() {
		String id = Session.getInstance().getRequestMap().get("projectID");

		if(id == null)
			id = "" + Session.getInstance().getSessionAtribute("projectID");	

		try {

			if(id != null && !id.isBlank()) {
				Project p = ProjectManager.findById(Integer.parseInt(id));
				if(p != null) {
					project = new ProjectContainer(p);
					ProjectManager.addView(p.getId());

					if(Session.getInstance().getUser() != null) {
						Candidate c = ProjectManager.getCandidature(Session.getInstance().getUser().getId(), project.getId());
						candidature = c == null ? new CandidateContainer() : new CandidateContainer(c);
					}
				}
			}

		}catch(Exception e) {
			System.err.println("(ProjectBean) Error parsing id or there is no project with the given id "+id+" : error type -> "+e.getClass());
		}
	}




	/**
	 * @return Returns if user that is in session already was liked this project
	 * if there is no user in session this will return false 
	 */
	public boolean wasLiked() {
		if(Session.getInstance().getUser() == null)
			return false;
		return ProjectManager.wasLiked(project.getId(),Session.getInstance().getUser().getId());
	}





	/**
	 * if there is no user logged in, this method will redirect user to login page.
	 * This method give a "like" to project if user didn't already liked it.
	 * if user already liked the project it will "dislike", 
	 * that means it will remove or add an like object to database.
	 */
	public void likeOrDislikeAction(ActionEvent event) {
		if(Session.getInstance().getUser() == null) { 
			redirectToLogin();
			return;
		}

		if(ProjectManager.wasLiked(project.getId(),Session.getInstance().getUser().getId())) 
			ProjectManager.dislike(project.getId(),Session.getInstance().getUser().getId());
		else
			ProjectManager.like(project.getId(),Session.getInstance().getUser().getId());
		
		project.refresh();
	}





	/**
	 * Redirects to login page
	 */
	public void redirectToLogin() {
		Session.getInstance().redirectToLogin("project");
		Session.getInstance().setSessionAtribute("projectID",project.getId());
	}





	/**
	 * Creates or edit candidature
	 */
	public void submitCandidature() {
		if(Session.getInstance().getUser() == null) { 
			redirectToLogin();
			return;
		}
		try {
			if(ProjectManager.getCandidature(Session.getInstance().getUser().getId(), project.getId()) == null) {
				candidature.setUser(Session.getInstance().getUser());
				ProjectManager.createCandidate(project.getId(), candidature);
			}
			else {
				Candidate c = ProjectManager.getCandidature(Session.getInstance().getUser().getId(), project.getId());
				c.update(candidature);
				JpaUtil.mergeEntity(c);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
