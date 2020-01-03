package com.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.database.entities.AcmLike;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.web.Session;
import com.web.containers.ProjectContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class ProjectBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ProjectContainer project = null;



	
	
	public ProjectBean() {
		String id = Session.getInstance().getRequestMap().get("projectID");
		
		if(id == null)
			id = "" + Session.getInstance().getSessionAtribute("projectID");	
		
		try {
			
			if(id != null && !id.isBlank())
				project = new ProjectContainer(Integer.parseInt(id));
			
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
		if(Session.getInstance().getUser() == null) { //
			Session.getInstance().redirectToLogin("project");
			Session.getInstance().setSessionAtribute("projectID",project.getId());
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
}
