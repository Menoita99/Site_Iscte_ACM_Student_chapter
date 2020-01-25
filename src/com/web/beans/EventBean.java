package com.web.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import com.database.managers.EventManager;
import com.web.Session;
import com.web.containers.EventContainer;

import lombok.Data;

@ManagedBean
@RequestScoped
@Data
public class EventBean implements Serializable{

	private static final long serialVersionUID = 1L; 

	private EventContainer event = null;

	public EventBean() {
		String id = Session.getInstance().getRequestMap().get("eventId");

		if(id == null)
			id = "" + Session.getInstance().getSessionAtribute("eventId");	
		
		try {
			if(id != null && !id.isBlank()) {
				event = new EventContainer(EventManager.getEventById(Integer.parseInt(id)));
				if(event != null)
					EventManager.addView(event.getId());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * @return Returns if user that is in session already was liked this project
	 * if there is no user in session this will return false 
	 */
	public boolean wasLiked() {
		if(Session.getInstance().getUser() == null)
			return false;
		return EventManager.wasLiked(event.getId(),Session.getInstance().getUser().getId());
	}
	
	
	
	
	
	
	/**
	 * if there is no user logged in, this method will redirect user to login page.
	 * This method give a "like" to event if user didn't already liked it.
	 * if user already liked the event it will "dislike", 
	 * that means it will remove or add an like object to database.
	 */
	public void likeOrDislikeAction(ActionEvent e) {
		if(Session.getInstance().getUser() == null) { 
			redirectToLogin();
			return;
		}

		if(EventManager.wasLiked(event.getId(),Session.getInstance().getUser().getId())) 
			EventManager.dislike(event.getId(),Session.getInstance().getUser().getId());
		else
			EventManager.like(event.getId(),Session.getInstance().getUser().getId());
		
		event.refresh();
	}




	/**
	 * Redirects to login page
	 */
	public void redirectToLogin() {
		Session.getInstance().redirectToLogin("event");
		Session.getInstance().setSessionAtribute("eventId",event.getId());
	}
	
}
