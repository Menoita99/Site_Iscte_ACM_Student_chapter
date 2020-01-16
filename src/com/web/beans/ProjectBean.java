package com.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.database.entities.AcmLike;
import com.database.entities.Project;
import com.database.entities.ProjectCandidate;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.web.Session;
import com.web.containers.ProjectCandidateContainer;
import com.web.containers.ProjectContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class ProjectBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private ProjectContainer project = null;
	private ProjectCandidateContainer candidature;



	/**
	 * Constructor
	 */
	public ProjectBean() {
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
						ProjectCandidate c = ProjectManager.getCandidature(Session.getInstance().getUser().getId(), project.getId());
						candidature = c == null ? new ProjectCandidateContainer() : new ProjectCandidateContainer(c);
					}
				}
			}

		}catch(Exception e) {
			System.err.println("(EventBean)[getEvent] Error parsing id or there is no project with the given id "+id+" : error type -> "+e.getClass());
		}
	}





	/**
	 * @return Returns if user that is in session already was liked this project
	 * if there is no user in session this will return false 
	 */
	public boolean wasLiked() {
		if(Session.getInstance().getUser() == null)
			return false;

		return ProjectManager.wasLiked(Session.getInstance().getUser().getId(), project.getId());
	}





	/**
	 * if there is no user logged in this method will redirect user to login page.
	 * This method "likes" an project if user didn't already liked the project or
	 * "dislikes", that mean it will remove or add an like object to database.
	 */
	public void likeOrDislikeAction(ActionEvent event) {
		if(Session.getInstance().getUser() == null) { 
			redirectToLogin();
			return;
		}

		List<AcmLike> likes = JpaUtil.executeQuery("Select l from AcmLike l where l.user.id = "+Session.getInstance().getUser().getId()
				+" and l.project.id = "+project.getId(), AcmLike.class);

		if(likes.isEmpty())
			ProjectManager.like(project.getId(),Session.getInstance().getUser().getId());
		else
			ProjectManager.dislike(likes.get(0));

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
	 * 
	 */
	public void submitCandidature() {
		try {
			if(candidature.getUser() == null && candidature.getProject() == null) {
				candidature.setUser(Session.getInstance().getUser());
				candidature.setProject(project);
				JpaUtil.createEntity(new ProjectCandidate(candidature));
			}else {
				ProjectCandidate p = ProjectManager.getCandidature(candidature.getUser().getId(), candidature.getProject().getId());
				p.setMotivation(candidature.getText());
				p.setDate(new Date(System.currentTimeMillis()));
				JpaUtil.mergeEntity(p);
			}
		}catch (Exception e) {
			System.out.println("(EventBean)[submitCandidature] candidature: "+candidature +" \nproject:  "+project);
			e.printStackTrace();
			//new facesMessage
			// TODO: handle exception
		}
	}

}
