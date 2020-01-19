package com.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.database.managers.UserManager;
import com.web.containers.ProjectContainer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

/**
 * Entity implementation class for Entity: Project
 */
@Entity
@Data
@NoArgsConstructor
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "project_views",
			   joinColumns = @JoinColumn(name = "project_id"),
			   inverseJoinColumns = @JoinColumn(name = "view_id"))
	private List<View> views = new ArrayList<>();
	
	@Column(nullable = false)
	private int maxMembers;
	
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
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadLine;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionDeadline;
	
	@Column(nullable = false)
	@Enumerated
	private State state = State.values()[new Random().nextInt(State.values().length-1)]; // State.ON_APPROVAL;  

	@Exclude
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_PROJECT_MANAGER_ID"), nullable= false)
 	private User manager;
	
	@Exclude
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> tags;
	
	@Exclude
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "project_participants",
			   joinColumns = @JoinColumn(name = "project_id"),
			   inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> participants;
	
	@Exclude
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> imagePath;
	
	@Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "project_material",
			   joinColumns = @JoinColumn(name = "project_id"),
			   inverseJoinColumns = @JoinColumn(name = "material_id"))
	private List<Material> material;
	
	@Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "project_likes",
			   joinColumns = @JoinColumn(name = "project_id"),
			   inverseJoinColumns = @JoinColumn(name = "like_id"))
	private List<AcmLike> likes = new ArrayList<>();
	
	
	
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
	public Project(int maxMembers, String title, String description, String shortDescription,String requirements, Date deadLine, Date subscriptionDeadline,
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
		this.shortDescription = shortDescription;
		
		List<User> participants = new ArrayList<>();
		participants.add(manager);
		this.participants = participants;
	}





	public Project(ProjectContainer p) {
		this.maxMembers = p.getMaxMembers();
		this.title = p.getTitle();
		this.description = p.getDescription();
		this.requirements = p.getRequirements();
		this.deadLine = p.getDeadline();
		this.subscriptionDeadline = p.getSubscriptionDeadline();
		this.manager = UserManager.getUserById(p.getManager().getId());
		this.tags = p.getTags(); 
		this.shortDescription = p.getShortDescription();
		this.participants = p.getParticipants().stream().map(pa ->UserManager.getUserById(pa.getId())).collect(Collectors.toList());
		this.material = p.getMaterial();
		if(p.getImagePath().isEmpty()) {
			this.imagePath = new ArrayList<>();
			this.imagePath.add("default/ACM_ICON.png");
		}else
		this.imagePath = p.getImagePath();
	}
	




	@Override
	public int hashCode() {
		return Objects.hash(deadLine, description, imagePath, maxMembers, requirements, state, subscriptionDeadline, tags, title, views);
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

