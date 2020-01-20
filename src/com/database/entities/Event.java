package com.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

/**
 * Entity implementation class for Entity: EventContainer
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Event implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private int vacancies;
	
	@Column(length = 65, nullable = false, unique = true)	
	private String title;
	
	@Exclude
	@Column(length = 665, nullable = false)	
	private String description;
	
	@Exclude
	@Column(length = 100, nullable = false)	
	private String shortDescription;
	
	@Exclude
	@Column(length = 300, nullable = false)	
	private String requirements;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date(System.currentTimeMillis());
	
	@Exclude
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> imagePath;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EVENT_USER_ID"), nullable= false)
 	private User manager;
 	
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> tags;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private State state = State.ON_APPROVAL;
	
	@Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "event_material",
			   joinColumns = @JoinColumn(name = "event_id"),
			   inverseJoinColumns = @JoinColumn(name = "material_id"))
	private List<Material> material = new ArrayList<>();
	
	@Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "event_views",
			   joinColumns = @JoinColumn(name = "event_id"),
			   inverseJoinColumns = @JoinColumn(name = "view_id"))
	private List<View> views = new ArrayList<>();
	
	@Exclude
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "event_participants",
			   joinColumns = @JoinColumn(name = "event_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> participants  = new ArrayList<>();
	
	@Exclude
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "event_staff",
			   joinColumns = @JoinColumn(name = "event_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> staff;
	
	@Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "event_likes",
			   joinColumns = @JoinColumn(name = "event_id"),
			   inverseJoinColumns = @JoinColumn(name = "like_id"))
	private List<AcmLike> likes  = new ArrayList<>();
	
	@Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "event",cascade=CascadeType.ALL)
	private List<EventInfo> infos = new ArrayList<>();
	
	
	/**
	 * @param vacancies
	 * @param title
	 * @param description
	 * @param shortDescription
	 * @param requirements
	 * @param imagePath
	 * @param dates
	 * @param manager
	 * @param tags
	 * @param material
	 * @param staff
	 * @param subscriptionDeadlines 
	 */
	public Event(int vacancies, String title, String description, String shortDescription, String requirements,
			List<String> imagePath, User manager, List<String> tags, List<User> staff) {
		this.vacancies = vacancies;
		this.title = title;
		this.description = description;
		this.shortDescription = shortDescription;
		this.requirements = requirements;
		this.imagePath =  imagePath.stream().map(path -> "events/"+path).collect(Collectors.toList());;
		this.manager = manager;
		this.tags = tags;
		this.staff = staff;
		this.staff.add(manager);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Event))
			return false;
		Event other = (Event) obj;
		return Objects.equals(creationDate, other.creationDate) && Objects.equals(description, other.description)
				&& id == other.id && Objects.equals(infos, other.infos)
				&& Objects.equals(requirements, other.requirements)
				&& Objects.equals(shortDescription, other.shortDescription) && state == other.state
				&& Objects.equals(tags, other.tags) && Objects.equals(title, other.title)
				&& vacancies == other.vacancies;
	}


	@Override
	public int hashCode() {
		return Objects.hash(creationDate, description, id, infos, requirements, shortDescription, state, tags, title,
				vacancies);
	}

}