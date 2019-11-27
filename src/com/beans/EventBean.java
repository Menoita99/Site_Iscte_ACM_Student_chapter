package com.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.containers.objects.EventContainer;
import com.containers.objects.UserContainer;
import com.database.entities.EventParticipant;
import com.database.managers.EventManager;
import com.web.Session;

@ManagedBean
@RequestScoped
public class EventBean implements Serializable{

	private static final long serialVersionUID = 1L; 

	private EventContainer event = null;

	private String errorMessage;

	
	
	

	/**
	 * @return return the event there is in request map
	 * if event there is no event inside requestMap it returns null
	 */
	public EventContainer getEvent() {
		String id = Session.getInstance().getRequestMap().get("id");
		
		if(id == null)
			id = "" + Session.getInstance().getSessionAtribute("eventID");	
		
		try {
			
			if(id != null && !id.isBlank() && (event == null || Integer.parseInt(id) != event.getId()))
				event = new EventContainer(Integer.parseInt(id));
			
		}catch(Exception e) {
			System.err.println("Error parsing id or there is no event with the given id : error type -> "+e.getClass());
			event=null;
		}
		
		return event;
	}





	/**
	 * if user isn't logged it will redirect user to login page to perform the login
	 * 
	 * When user is logged it will verify if he is already a participant ,
	 * and in case he's not it will add him to database.
	 * 
	 * In case event is full it will display an error message
	 * 
	 */
	public String join() {
		UserContainer user = Session.getInstance().getUser();

		if(user == null) {
			Session.getInstance().setLastPage("event");
			Session.getInstance().setSessionAtribute("eventID", event.getId());
			return "login";
		}


		if(!hasJoin()) {
			EventParticipant ep = EventManager.addParticipant(event.getId(), user.getId(), false);
			if(ep == null)
				setErrorMessage("Event is full");
		}

		return "";
	}







	/**
	 * if user isn't logged it will redirect user to login page to perform the login
	 * 
	 * When user is logged it will verify if he is already a participant ,
	 * and in case he's it will remove him from database.
	 */
	public String removeJoin() {
		UserContainer user = Session.getInstance().getUser();

		if(user == null) return "login";

		if(hasJoin()) {
			EventParticipant ep = EventManager.removeParticipant(event.getId(), user.getId());
			if(ep == null)
				setErrorMessage("An error occurred removing user, if this persists please contact us");
		}

		return "";
	}







	/**
	 * @return return false if user isn't logged or if user isn't a participant of this event
	 * 			otherwise returns true.
	 */
	public boolean hasJoin() {
		UserContainer user = Session.getInstance().getUser();
		if(user == null) return false;
		return EventManager.isParticipant(event.getId(),user.getId());
	}






	/**
	 * @return the erroMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}






	/**
	 * @param erroMessage the erroMessage to set
	 */
	public void setErrorMessage(String erroMessage) {
		this.errorMessage = erroMessage;
	}
}
