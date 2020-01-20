package com.web.beans.admin;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.managers.ProjectManager;
import com.web.containers.ProjectContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class AdminProjectsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<ProjectContainer> projects;
	
	public AdminProjectsBean() {
		projects = ProjectManager.findAll().stream().map(ProjectContainer::new).collect(Collectors.toList());
	}
}
