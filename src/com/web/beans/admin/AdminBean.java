package com.web.beans.admin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.web.Session;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@RequestScoped
@Data
public class AdminBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private UserContainer user;

	@PostConstruct
	public void init() {
		UserContainer u = Session.getInstance().getUser();

		if(u == null) 
			Session.getInstance().redirectToLogin("/pages/admin/adminProjects");
		else if(!u.isAdmin())
			Session.getInstance().redirectWithContext("/pages/unauthorizationError.xhtml");
		else
			user = u;
	}
}
