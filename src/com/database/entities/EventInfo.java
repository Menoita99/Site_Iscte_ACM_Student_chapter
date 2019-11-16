package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.database.converters.LocalDateTimeAttributeConverter;

/**
 * Entity implementation class for Entity: EventInfo
 *
 */
@Entity
@Table(name = "event_info")
public class EventInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private int id;
	
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(nullable = false)
	private LocalDateTime startDate;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(nullable = false)
	private LocalDateTime endDate;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EVENT_INFO_ID"), nullable= false)
	private Event event;
	
	@Column(nullable = false)
	private String place;

	
	
	
	
	
	/**
	 * @return return the day of startDate attribute
	 */
	public int getStartDateDay() {
		return startDate.getDayOfMonth();
	}
	
	
	
	
	
	
	/**
	 * @return return the month from startDate as a 3 letters format text
	 * 		e.g: JANUARY -> JAN
	 */
	public String getStartDateMonth() {
		return startDate.getMonth().toString().toUpperCase().substring(0, 3);
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * @return return a string with the startDate hours
	 * 	e.g-> 20:30 
	 */
	public String getStartDateHours() {
		return startDate.getHour() + ":" + startDate.getMinute();
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * @return return a string with the endDate hours
	 * 	e.g-> 20:30 
	 */
	public String getEndDateHours() {
		return endDate.getHour() + ":" + endDate.getMinute();
	}
	
	
	
	
	
	//--------- GETTERS AND SETTERS
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

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
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, event, id, place, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventInfo))
			return false;
		EventInfo other = (EventInfo) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(event, other.event) && id == other.id
				&& Objects.equals(place, other.place) && Objects.equals(startDate, other.startDate);
	}

	@Override
	public String toString() {
		return "EventInfo [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", event=" + event
				+ ", place=" + place + "]";
	}
}