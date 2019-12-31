package com.web.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
			
			if(id != null && !id.isBlank() && (project == null || Integer.parseInt(id) != project.getId()))
				project = new ProjectContainer(Integer.parseInt(id));
			
		}catch(Exception e) {
			System.err.println("(EventBean)[getEvent] Error parsing id or there is no project with the given id "+id+" : error type -> "+e.getClass());
		}
	}
}
