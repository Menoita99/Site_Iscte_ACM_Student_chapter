package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: Project
 *
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "project_id")
	private int id;
	
	@Column(nullable = false)
	private int views = 0;
	
	@Column(nullable = false)
	private int maxMembers;
	
	@Column(length = 65, nullable = false, unique = true)	
	private String title;
	
	@Column(length = 665, nullable = false)	
	private String description;
	
	@Column()
	@ElementCollection(targetClass=String.class)
	private List<String> imagePath;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_PROJECT_USER_ID"), nullable= false)
 	private User manager;
	
	@Column(nullable = false)
	private LocalDateTime deadLine;
	
	@Column(nullable = false)
	private LocalDateTime subscriptionDeadline;
	
	@Column(nullable = false)
	private State state;

	@Column()
	@ElementCollection(targetClass=String.class)
	private List<String> tags;
	
	@Column()
	@ElementCollection(targetClass=User.class)
	private List<User> participants;
	

	
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
	 * @return the maxMembers
	 */
	public int getMaxMembers() {
		return maxMembers;
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
	 * @return the deadLine
	 */
	public LocalDateTime getDeadLine() {
		return deadLine;
	}

	/**
	 * @return the subscriptionDeadline
	 */
	public LocalDateTime getSubscriptionDeadline() {
		return subscriptionDeadline;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
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
	 * @param maxMembers the maxMembers to set
	 */
	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
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
	 * @param deadLine the deadLine to set
	 */
	public void setDeadLine(LocalDateTime deadLine) {
		this.deadLine = deadLine;
	}

	/**
	 * @param subscriptionDeadline the subscriptionDeadline to set
	 */
	public void setSubscriptionDeadline(LocalDateTime subscriptionDeadline) {
		this.subscriptionDeadline = subscriptionDeadline;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deadLine, description, id, imagePath, manager, maxMembers, state, subscriptionDeadline,
				title, views);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Project))
			return false;
		Project other = (Project) obj;
		return Objects.equals(deadLine, other.deadLine) && Objects.equals(description, other.description)
				&& id == other.id && Objects.equals(imagePath, other.imagePath)
				&& Objects.equals(manager, other.manager) && maxMembers == other.maxMembers && state == other.state
				&& Objects.equals(subscriptionDeadline, other.subscriptionDeadline)
				&& Objects.equals(title, other.title) && views == other.views;
	}
}
