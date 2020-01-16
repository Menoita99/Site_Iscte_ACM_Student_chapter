package com.web.beans.admin;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.database.managers.ProjectManager;
import com.web.Session;
import com.web.containers.ProjectContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class AdminProjectBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProjectContainer project;
	private String managerUsername;
	
	

	public AdminProjectBean() {
		String id = Session.getInstance().getRequestMap().get("projectId");

		if(id == null)
			id = "" + Session.getInstance().getSessionAtribute("projectId");

		try {
			if(id != null) {
				project = new ProjectContainer(ProjectManager.findById(Integer.parseInt(id)));
				managerUsername = project.getManager().getUsername();
			}
		}catch(NumberFormatException e) {
			System.out.println("Couldn't parse "+id);
			e.printStackTrace();
		}
	}
	
	
	public void saveDeatils(ActionEvent event) {
		ProjectManager.updateProject(project);
	}

}
