package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: EventRespect
 *
 */
@Entity
public class EventRespect implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EventRespectID id;
	
	@MapsId("eventID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	private Event event;

	@MapsId("userID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	private User user;
	
	@Column(nullable = false)
	private LocalDateTime date;

	
	
	/**
	 * @return the id
	 */
	public EventRespectID getId() {
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
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(EventRespectID id) {
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

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, event, id, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventRespect))
			return false;
		EventRespect other = (EventRespect) obj;
		return Objects.equals(date, other.date) && Objects.equals(event, other.event) && Objects.equals(id, other.id)
				&& Objects.equals(user, other.user);
	}
	
	
	
}
