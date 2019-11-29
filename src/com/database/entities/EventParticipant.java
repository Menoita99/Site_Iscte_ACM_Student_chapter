package com.database.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: EventParticipant
 *
 */
@Entity
@Table(name = "event_participant")
public class EventParticipant implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column
	private boolean isStaff = false;
	
	@EmbeddedId
	private EventParticipantID id = new EventParticipantID();

	@MapsId("eventID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne
	private Event event;

	@MapsId("userID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne
	private User user;

	
	
	/**
	 * @return the isStaff
	 */
	public boolean isStaff() {
		return isStaff;
	}

	/**
	 * @return the id
	 */
	public EventParticipantID getId() {
		return id;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param isStaff the isStaff to set
	 */
	public void setStaff(boolean isStaff) {
		this.isStaff = isStaff;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(EventParticipantID id) {
		this.id = id;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(event, id, isStaff, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventParticipant))
			return false;
		EventParticipant other = (EventParticipant) obj;
		return Objects.equals(event, other.event) && Objects.equals(id, other.id) && isStaff == other.isStaff
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "EventParticipant [isStaff=" + isStaff + ", id=" + id + ", event=" + event + ", user=" + user + "]";
	}
}
