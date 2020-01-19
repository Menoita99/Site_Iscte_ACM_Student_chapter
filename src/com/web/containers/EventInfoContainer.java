package com.web.containers;

import java.io.Serializable;
import java.util.Date;

import com.database.entities.EventInfo;
import com.database.managers.EventManager;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
public class EventInfoContainer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date eventDate;
	private Date joinDate;
	private String place;
	
	@Exclude
	@lombok.ToString.Exclude
	private EventContainer event;
	
	public EventInfoContainer(EventInfo ei) {
		this.id = ei.getId();
		this.eventDate = ei.getEventDate();
		this.joinDate = ei.getJoinDate();
		this.place = ei.getPlace();
	}
	
	
	public EventContainer getEvent() {
		if(event == null)
			 event = new EventContainer(EventManager.getInfo(id).getEvent());
		return event;
	}
}
