package com.web.containers;

import java.io.Serializable;
import java.util.List;

import com.database.entities.Research;
import com.database.entities.State;
import com.database.entities.User;

import lombok.Data;
import lombok.ToString.Exclude;

@Data
public class ResearchContainer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String description;
	private String shortDescription;
	private String requirements;
	private State state;
	private List<String> tags;
	private List<String> imagePath;
	private int likes;
	private int views;

	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private InvestigatorContainer investigator = null;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<User> participants = null;
	
	
	
	
	public ResearchContainer(Research r) {
		id=r.getId();
		title = r.getTitle();
		description = r.getDescription();
		shortDescription = r.getShortDescription();
		requirements = r.getRequirements();
		state = r.getState();
		tags = r.getTags();
		imagePath = r.getImagePath();
		likes = r.getLikes().size();
		views = r.getViews().size();
	}
}
