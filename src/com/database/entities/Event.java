package com.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: EventContainer
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "event_id")
	private int id;
	
	@Column(nullable = false)
	private int views = 0;	
	
	@Column(nullable = false)
	private int vacancies;
	
	@Column(length = 65, nullable = false, unique = true)	
	private String title;
	
	@Column(length = 750, nullable = false)
	private String description;
	
	@Column(length = 300)
	private String requirements;
	
	@Column(length = 500)
	private String observation;
	
	@Column
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> imagePath = new LinkedHashSet<>();
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EVENT_USER_ID"), nullable= false)
 	private User manager;
 	
	@Column()
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> tags = new LinkedHashSet<>();
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private State state;
	
	@Column()
	private Double budget;
	
	
	
	/**
	 * This method adds an image to imagePath
	 * @param image image path
	 */
	public void addImage(String image) {
		imagePath.add(image);
	}
	
	
	/**
	 * Add all tags from a Collection 
	 * @param tags collection with tags to be added
	 */
	public void addAllTags(Collection<String> tags) {
		this.tags.addAll(tags);
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the views
	 */
	public int getViews() {
		return views;
	}

	/**
	 * @return the vacancies
	 */
	public int getVacancies() {
		return vacancies;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the imagePath
	 */
	public List<String> getImagePath() {
		List<String> list = new ArrayList<String>();
		list.addAll(imagePath);
		return list;
	}

	/**
	 * @return the manager
	 */
	public User getManager() {
		return manager;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		List<String> list = new ArrayList<String>();
		list.addAll(tags);
		return list;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @return the requirements
	 */
	public String getRequirements() {
		return requirements;
	}


	/**
	 * @return the budget
	 */
	public Double getBudget() {
		return budget;
	}


	/**
	 * @return the observation
	 */
	public String getObservation() {
		return observation;
	}


	/**
	 * @param observation the observation to set
	 */
	public void setObservation(String observation) {
		this.observation = observation;
	}


	/**
	 * @param budget the budget to set
	 */
	public void setBudget(Double budget) {
		this.budget = budget;
	}


	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param views the views to set
	 */
	public void setViews(int views) {
		this.views = views;
	}

	/**
	 * @param vacancies the vacancies to set
	 */
	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(Set<String> imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(User manager) {
		this.manager = manager;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}


	@Override
	public int hashCode() {
		return Objects.hash(budget, description, id, imagePath, manager, requirements, state, tags, title, vacancies,
				views);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Event))
			return false;
		Event other = (Event) obj;
		return Objects.equals(budget, other.budget) && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(imagePath, other.imagePath) && Objects.equals(manager, other.manager)
				&& Objects.equals(requirements, other.requirements) && state == other.state
				&& Objects.equals(tags, other.tags) && Objects.equals(title, other.title)
				&& vacancies == other.vacancies && views == other.views;
	}


	@Override
	public String toString() {
		return "Event [id=" + id + ", views=" + views + ", vacancies=" + vacancies + ", title=" + title
				+ ", description=" + description + ", requirements=" + requirements + ", imagePath=" + imagePath
				+ ", manager=" + manager + ", tags=" + tags + ", state=" + state + ", budget=" + budget + "]";
	}


}