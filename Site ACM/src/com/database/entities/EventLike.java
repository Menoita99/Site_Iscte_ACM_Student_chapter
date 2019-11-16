package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: EventLike
 *
 */
@Entity

public class EventLike implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Column(nullable = false)
	private LocalDateTime date;
	
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
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
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
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
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
		return Objects.hash(date, event, id, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventLike))
			return false;
		EventLike other = (EventLike) obj;
		return Objects.equals(date, other.date) && Objects.equals(event, other.event) && Objects.equals(id, other.id)
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "EventLike [date=" + date + ", id=" + id + ", event=" + event + ", user=" + user + "]";
	}
}
