package com.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.containers.objects.EventContainer;
import com.containers.objects.UserContainer;
import com.database.managers.EventManager;
import com.web.Session;

@ManagedBean
@RequestScoped
public class EventBean implements Serializable{
	
	private static final long serialVersionUID = 1L; 

	
	private EventContainer event = null;


	/**
	 * @return return the event there is in request map
	 * if event there is no event inside requestMap it returns null
	 */
	public EventContainer getEvent() {
		String id = Session.getInstance().getRequestMap().get("id");
		if(id != null && !id.isBlank() && (event == null || Integer.parseInt(id) != event.getId()))
				event = new EventContainer(Integer.parseInt(id));
		return event;
	}
	
	
	
	
	
	/**
	 * 
	 * @return
	 */
	public String join() {
		UserContainer user = Session.getInstance().getUser();
		if(user == null) return "login";
		
		
		
		return "";
	}
	
	
	
	
	
	/**
	 * 
	 */
	public boolean hasJoin() {
		UserContainer user = Session.getInstance().getUser();
		if(user == null) return false;
		return EventManager.isParticipant(event.getId(),user.getId());
	}
}
