package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: ProjectRespect
 *
 */
@Entity
public class ProjectRespect implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProjectRespectID id;
	
	@MapsId("projectID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	private Project project;

	@MapsId("userID")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne
	private User user;
	
	@Column(nullable = false)
	private LocalDateTime date;

	
	
	/**
	 * @return the id
	 */
	public ProjectRespectID getId() {
		return id;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
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
	public void setId(ProjectRespectID id) {
		this.id = id;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
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
		return Objects.hash(date, id, project, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProjectRespect))
			return false;
		ProjectRespect other = (ProjectRespect) obj;
		return Objects.equals(date, other.date) && Objects.equals(id, other.id)
				&& Objects.equals(project, other.project) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "ProjectRespect [id=" + id + ", project=" + project + ", user=" + user + ", date=" + date + "]";
	}
	
}
