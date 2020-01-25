package com.web.containers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.database.entities.EventInfo;
import com.database.managers.EventManager;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
public class EventInfoContainer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private LocalDateTime startEventDate;
	private Date joinDate;
	private String place;
	
	private Date duration;
	private int day;
	
	
	
	@Exclude
	@lombok.ToString.Exclude
	private EventContainer event;
	
	public EventInfoContainer(EventInfo ei) {
		this.id = ei.getId();
		this.startEventDate = ei.getStartEventDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.joinDate = ei.getJoinDate();
		this.place = ei.getPlace();
//		startEventDate.get
	}
	
	
	
	public EventContainer getEvent() {
		if(event == null)
			 event = new EventContainer(EventManager.getInfo(id).getEvent());
		return event;
	}
}
