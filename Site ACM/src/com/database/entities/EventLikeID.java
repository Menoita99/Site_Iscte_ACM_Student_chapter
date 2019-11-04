package com.database.entities;

import java.io.Serializable;
import java.util.Objects;


import javax.persistence.*;

/**
 * Entity implementation class for Entity: EventLikeID
 *
 */
@Embeddable
public class EventLikeID implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private int userID;
	
	@Column(nullable = false)
	private int eventID;

	@Override
	public int hashCode() {
		return Objects.hash(eventID, userID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventLikeID))
			return false;
		EventLikeID other = (EventLikeID) obj;
		return eventID == other.eventID && userID == other.userID;
	}
}
