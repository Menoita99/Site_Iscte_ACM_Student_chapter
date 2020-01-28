package com.database.entities;

import com.database.entities.Event;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: EventInfo
 *
 */
@Entity
@Table(name="event_info")
@Data
@NoArgsConstructor
public class EventInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private int vacancies;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startEventDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endEventDate;
	
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
	
	@Exclude
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "event_info_participants",
			   joinColumns = @JoinColumn(name = "event_info_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> participants  = new ArrayList<>();
	
	@Exclude
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "event_info_staff",
			   joinColumns = @JoinColumn(name = "event_info_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> staff;
	
	
	/**
	 * @param eventDate
	 * @param joinDate
	 * @param place
	 * @param event
	 * @param staff 
	 */
	public EventInfo(Date startEventDate,Date endEventDate, Date joinDate, String place, Event event, List<User> staff) {
		this.startEventDate = startEventDate;
		this.endEventDate = endEventDate;
		this.joinDate = joinDate;
		this.place = place;
		this.event = event;
		this.staff = staff;
		this.staff.add(event.getManager());
	}

}
