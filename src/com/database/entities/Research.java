package com.database.entities;

import com.database.entities.Investigator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * Entity implementation class for Entity: Research
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Research implements Serializable {

	private static final long serialVersionUID = 1L;   

	@Id
	@lombok.EqualsAndHashCode.Exclude
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Exclude
	@ManyToMany
	@lombok.EqualsAndHashCode.Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "research_investigators",
	joinColumns = @JoinColumn(name = "research_id"),
	inverseJoinColumns = @JoinColumn(name = "investigator_id"))
	private List<Investigator> investigators;

	@Column(length = 65, nullable = false, unique = true)	
	private String title;

	@Exclude
	@Column(length = 665, nullable = false)	
	private String description;

	@Exclude
	@Column(length = 100, nullable = false)	
	private String shortDescription;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date(System.currentTimeMillis());

	@Exclude
	@Column(length = 300, nullable = false)	
	private String requirements;

	@Column(nullable = false)
	@Enumerated
	private State state = State.values()[new Random().nextInt(State.values().length-1)]; // State.ON_APPROVAL;  

	@Exclude
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> tags;

	@Exclude
	@ManyToMany
	@lombok.EqualsAndHashCode.Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "research_participants",
	joinColumns = @JoinColumn(name = "research_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> participants;

	@Exclude
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> imagePath;

	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "research_likes",
	joinColumns = @JoinColumn(name = "research_id"),
	inverseJoinColumns = @JoinColumn(name = "like_id"))
	private List<AcmLike> likes = new ArrayList<>();

	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "research_views",
	joinColumns = @JoinColumn(name = "research_id"),
	inverseJoinColumns = @JoinColumn(name = "view_id"))
	private List<View> views = new ArrayList<>();


	@Enumerated
	private ResearchType type;

	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "research_candidates",
	joinColumns = @JoinColumn(name = "research_id"),
	inverseJoinColumns = @JoinColumn(name = "candidate_id"))
	private List<Candidate> candidates = new ArrayList<>();

	@Exclude
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> institutions;
	
	
	@Exclude
	@ElementCollection(targetClass=String.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> usefullLinks;
	




	/**
	 * @param investigator
	 * @param title
	 * @param description
	 * @param shortDescription
	 * @param requirements
	 * @param tags
	 * @param imagePath
	 * @param type
	 */
	public Research(List<String> institutions,List<String> usefullLinks,List<Investigator> investigators, String title, String description, String shortDescription,
			String requirements, List<String> tags, List<String> imagePath, ResearchType type) {
		
		if(investigators.isEmpty()) throw new IllegalArgumentException("investigators can't be empty");
		if(institutions.isEmpty()) throw new IllegalArgumentException("institutions can't be empty");
		
		this.investigators = investigators;
		this.title = title;
		this.description = description;
		this.shortDescription = shortDescription;
		this.requirements = requirements;
		this.tags = tags;
		this.imagePath = imagePath.stream().map(path -> "research/"+path).collect(Collectors.toList());
		this.type = type;
		this.institutions = institutions;
		this.usefullLinks = usefullLinks;
	}
}
