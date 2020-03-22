package com.web.containers;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.database.entities.Event;
import com.database.entities.Material;
import com.database.entities.State;
import com.database.managers.EventManager;
import com.utils.comparators.NearestFutureComparator;

import lombok.Data;
import lombok.ToString.Exclude;

@Data
public class EventContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int likes;
	private int views;
	private String title;
	private String description;
	private String shortDescription;
	private String requirements;
	private State state ;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
 	private UserContainer manager;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<String> imagePath;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<String> tags;
	
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<Material> material;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<UserContainer> staff;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private List<EventInfoContainer> infos;
	
	
	
	
	
	
	public EventContainer(Event e){
		this.id = e.getId();
		this.likes = e.getLikes().size();
		this.views = e.getViews().size();
		this.title = e.getTitle();
		this.description = e.getDescription();
		this.shortDescription = e.getShortDescription();
		this.requirements = e.getRequirements();
		this.state = e.getState();
		this.imagePath = e.getImagePath();
		this.tags = e.getTags();
		this.material = e.getMaterial();
	}
	
	
	
	
	
	
	public EventContainer(int id){
		Event e = EventManager.getEventById(id);
		this.id = e.getId();
		this.likes = e.getLikes().size();
		this.views = e.getViews().size();
		this.title = e.getTitle();
		this.description = e.getDescription();
		this.shortDescription = e.getShortDescription();
		this.requirements = e.getRequirements();
		this.state = e.getState();
		this.imagePath = e.getImagePath();
		this.tags = e.getTags();
		this.material = e.getMaterial();
	}

	
	
	
	
	public UserContainer getManager() {
		if(manager == null) 
			manager = new UserContainer(EventManager.getEventById(id).getManager());
		return manager;
	}

	
	
	
	
	/**
	 * @return returns event staff
	 */
	public List<UserContainer> getStaff() {
		if(staff == null) 
			staff = EventManager.getStaff(id).stream().map(UserContainer::new).collect(Collectors.toList());
		return staff;
	}

	
	
	
	
	/**
	 * @return returns event participants
	 */
	public List<EventInfoContainer> getInfos() {
		if(infos == null) { 
			infos = EventManager.getEventById(id).getInfos().stream().map(EventInfoContainer::new).collect(Collectors.toList());
			infos.sort((EventInfoContainer o1, EventInfoContainer o2) -> new NearestFutureComparator().compare(o1.getStartEventDate(),o2.getStartEventDate()));
		}
		return infos;
	}

	
	
	
	
	public void refresh() {
		Event e = EventManager.getEventById(id);
		this.id = e.getId();
		this.likes = e.getLikes().size();
		this.views = e.getViews().size();
		this.title = e.getTitle();
		this.description = e.getDescription();
		this.shortDescription = e.getShortDescription();
		this.requirements = e.getRequirements();
		this.state = e.getState();
		this.imagePath = e.getImagePath();
		this.tags = e.getTags();
		this.material = e.getMaterial();
		this.staff = null;
		this.infos = null;
		this.manager = null;
	}
}
