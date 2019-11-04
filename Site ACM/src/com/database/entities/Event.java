package com.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

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
	
	@Column(length = 665, nullable = false)
	private String description;
	
	@Column
	@ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
	private List<String> imagePath = new ArrayList<>();
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EVENT_USER_ID"), nullable= false)
 	private User manager;
 	
	@Column()
	@ElementCollection(targetClass=String.class)
	private List<String> tags = new ArrayList<>();
	
	@Column(nullable = false)
	private State state;
	
	
	
	
	
	
	
	
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
		return imagePath;
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
		return tags;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
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
	public void setImagePath(List<String> imagePath) {
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
	public void setTags(List<String> tags) {
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
		return Objects.hash(description, id, imagePath, manager, state, tags, title, vacancies, views);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Event))
			return false;
		Event other = (Event) obj;
		return Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(imagePath, other.imagePath) && Objects.equals(manager, other.manager)
				&& state == other.state && Objects.equals(tags, other.tags) && Objects.equals(title, other.title)
				&& vacancies == other.vacancies && views == other.views;
	}

}