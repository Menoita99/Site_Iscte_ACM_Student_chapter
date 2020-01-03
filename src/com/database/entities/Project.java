package com.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Date;
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
 * Entity implementation class for Entity: Project
 *
 */
@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	
	@Column(nullable = false)
	private int views = 0;
	
	
	@Column(nullable = false)
	private int maxMembers;
	
	
	@Column(length = 65, nullable = false, unique = true)	
	private String title;
	
	@Exclude
	@Column(length = 665, nullable = false)	
	private String description;
	
	@Exclude
	@Column(length = 300, nullable = false)	
	private String requirements;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadLine;
	
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionDeadline;
	
	
	@Column(nullable = false)
	@Enumerated
	private State state = State.ON_APPROVAL;  //State.values()[new Random().nextInt(State.values().length)]; to random generate


	@Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_PROJECT_MANAGER_ID"), nullable= false)
 	private User manager;
	
	@Exclude
	@Column
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> tags;
	
	
	@Exclude
	@Column
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "project_participants",
			   joinColumns = @JoinColumn(name = "project_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> participants;
	
	@Exclude
	@Column
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> imagePath;
	
	
	
	
	
	
	
	/**
	 * @param maxMembers number of maximum members of a team
	 * @param title	project title
	 * @param description project description
	 * @param deadLine	project deadline
	 * @param subscriptionDeadline project subscription deadline
	 * @param manager	project manager 
	 * @param tags	project tags
	 * @param imagePath	project images
	 */
	public Project(int maxMembers, String title, String description,String requirements, Date deadLine, Date subscriptionDeadline,
			User manager, List<String> tags, List<String> imagePath) {

		this.maxMembers = maxMembers;
		this.title = title;
		this.description = description;
		this.requirements = requirements;
		this.deadLine = deadLine;
		this.subscriptionDeadline = subscriptionDeadline;
		this.manager = manager;
		this.tags = tags; 
		this.imagePath = imagePath.stream().map(path -> "projects/"+path).collect(Collectors.toList());
		
		List<User> participants = new ArrayList<>();
		participants.add(manager);
		this.participants = participants;
	}





	@Override
	public int hashCode() {
		return Objects.hash(deadLine, description, imagePath, maxMembers, requirements, state, subscriptionDeadline,
				tags, title, views);
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Project))
			return false;
		Project other = (Project) obj;
		return Objects.equals(deadLine, other.deadLine) && Objects.equals(description, other.description)
				&& Objects.equals(imagePath, other.imagePath) && maxMembers == other.maxMembers
				&& Objects.equals(requirements, other.requirements) && state == other.state
				&& Objects.equals(subscriptionDeadline, other.subscriptionDeadline) && Objects.equals(tags, other.tags)
				&& Objects.equals(title, other.title) && views == other.views;
	}
}

