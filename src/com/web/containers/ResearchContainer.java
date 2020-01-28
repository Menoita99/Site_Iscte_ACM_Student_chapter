package com.web.containers;

import java.io.Serializable;
import java.util.List;

import com.database.entities.Research;
import com.database.entities.ResearchType;
import com.database.entities.State;
import com.database.entities.User;
import com.database.managers.ResearchManager;

import lombok.Data;
import lombok.ToString.Exclude;

@Data
public class ResearchContainer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private State state;
	private int likes;
	private int views;
	private ResearchType type;
	
	@Exclude
	private String description;
	@Exclude
	private String shortDescription;
	@Exclude
	private String requirements;
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private InvestigatorContainer investigator = null;
	@Exclude
	private List<String> tags;
	@Exclude
	private List<String> imagePath;
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
		investigator = new InvestigatorContainer(r.getInvestigator());
		type = r.getType();
	}




	public void refresh() {
		Research r = ResearchManager.findResearch(id);
		title = r.getTitle();
		description = r.getDescription();
		shortDescription = r.getShortDescription();
		requirements = r.getRequirements();
		state = r.getState();
		tags = r.getTags();
		imagePath = r.getImagePath();
		likes = r.getLikes().size();
		views = r.getViews().size();
		investigator = new InvestigatorContainer(r.getInvestigator());
		type = r.getType();
	}
}
