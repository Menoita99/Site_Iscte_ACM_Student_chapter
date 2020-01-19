package com.database.entities;

import com.database.entities.Event;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: EventInfo
 *
 */
@Entity
//@Table(name="event_info")
@Data
@NoArgsConstructor
public class EventInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date joinDate;
	
	@Column(length = 250)
	private String place;

	@Exclude
	@lombok.ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="event_id",nullable = false)
	private Event event;
	
	
	
	/**
	 * @param eventDate
	 * @param joinDate
	 * @param place
	 * @param event
	 */
	public EventInfo(Date eventDate, Date joinDate, String place, Event event) {
		this.eventDate = eventDate;
		this.joinDate = joinDate;
		this.place = place;
		this.event = event;
	}

}
