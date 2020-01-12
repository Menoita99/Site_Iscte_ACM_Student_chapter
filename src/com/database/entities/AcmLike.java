package com.database.entities;

import com.database.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * Entity implementation class for Entity: AcmLike
 *
 */
@Entity
@Data
@NoArgsConstructor
public class AcmLike implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Exclude
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_LIKE_ID"), nullable= false)
	private User user;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date =  new Date(System.currentTimeMillis());

	@Exclude
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_PROJECT_LIKE_ID"), nullable= true)
	private Project project = null;
	
	
	@Exclude
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EVENT_LIKE_ID"), nullable= true)
	private Event event = null;
	
	
	/**
	 * @param user
	 * @param project
	 */
	public AcmLike(User user, Project project) {
		this.user = user;
		this.project = project;
	}
	
	
	/**
	 * @param user
	 * @param event
	 */
	public AcmLike(User user,Event event) {
		this.user = user;
		this.event = event;
	}
}
