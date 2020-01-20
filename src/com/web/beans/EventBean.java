package com.web.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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

	/**
	 * Search for event id to set event
	 */
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
	
}
