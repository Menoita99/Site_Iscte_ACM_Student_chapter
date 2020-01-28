package com.web.beans.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.managers.UserManager;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class AdminUserBean {
	
	private List<UserContainer> users;

	
	@PostConstruct
	public void init() {
		users = UserManager.findAll().stream().map(UserContainer::new).collect(Collectors.toList());
//		pendingProjects = JpaUtil.executeQuery("Select count(*) from Project p where p.state = "+ State.ON_APPROVAL.ordinal(), Long.class) .get(0);
//		projectsDeveloping= JpaUtil.executeQuery("Select count(*) from Project p where p.state = "+ State.DEVELOPING.ordinal(), Long.class) .get(0);
//		viewsMonth = getViewsPerMonth();
//		likesMonth = getLikesPerMonth();
	}
}
