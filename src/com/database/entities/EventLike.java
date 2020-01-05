package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

/**
 * Entity implementation class for Entity: EventLike
 *
 */
@Entity
@Data
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

}
