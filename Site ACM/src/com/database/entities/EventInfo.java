package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: EventInfo
 *
 */
@Entity
@Table(name = "event_info")
public class EventInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EVENT_INFO_EVENT_ID"), nullable= false,referencedColumnName = "event_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private int eventID;
	
	@Column(nullable = false)
	private String place;
	
	@Id
	@GeneratedValue
	private int id;

	
	
	
	/**
	 * @return the startDate
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * @return the eventID
	 */
	public int getEventID() {
		return eventID;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, eventID, id, place, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventInfo))
			return false;
		EventInfo other = (EventInfo) obj;
		return Objects.equals(endDate, other.endDate) && eventID == other.eventID && id == other.id
				&& Objects.equals(place, other.place) && Objects.equals(startDate, other.startDate);
	}
	
	
}



































/*
 * 
 * 
 * 	
	@EmbeddedId
	private EventInfoId infoID;
	private String place;
	
	@MapsId("infoEventId")
	@OneToOne
	private EventContainer event;
 * 
 * 
 * 
 * 
 * 
 * 
 * package com.database;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;


@Embeddable
public class EventInfoId implements Serializable {


	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@Column(nullable = false)
	private int infoEventId;

	
	
	@Override
	public int hashCode() {
		return Objects.hash(endDate, startDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventInfoId))
			return false;
		EventInfoId other = (EventInfoId) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(startDate, other.startDate);
	}

}*/
