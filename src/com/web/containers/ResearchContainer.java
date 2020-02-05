package com.web.containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private List<InvestigatorContainer> investigators;
	@Exclude
	private List<String> tags = new ArrayList<>();
	@Exclude
	private List<String> imagePath = new ArrayList<>();
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<User> participants;
	@Exclude
	private List<String> usefulllinks = new ArrayList<>();
	@Exclude
	private List<String> institutions = new ArrayList<>();
	
	
	
	
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
		type = r.getType();
		institutions = r.getInstitutions();
		usefulllinks = r.getUsefullLinks();
	}

	
	
	
	
	
	public ResearchContainer(InvestigatorContainer inv) {
		participants  = new ArrayList<>();
		investigators  = new ArrayList<>();
		investigators.add(inv);
	}
	



	
	
	
	public List<InvestigatorContainer> getInvestigators(){
		if(investigators == null)
			investigators = ResearchManager.findResearch(id).getInvestigators().stream().map(InvestigatorContainer::new).collect(Collectors.toList());
		return investigators;
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
		type = r.getType();
		institutions = r.getInstitutions();
		usefulllinks = r.getUsefullLinks();
	}
	
	
	
	
	
}
