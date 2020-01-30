package com.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.managers.UserManager;
import com.web.Session;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class PerfilBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private UserContainer user;
	
	@PostConstruct
	public void init() {
		String id = Session.getInstance().getRequestMap().get("userID");
		try {
			int i = Integer.parseInt(id);
			user = new UserContainer(UserManager.getUserById(i));
			if(user != null)
				UserManager.addView(user.getId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
