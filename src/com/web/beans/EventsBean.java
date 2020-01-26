package com.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.database.entities.Event;
import com.database.entities.State;
import com.database.managers.EventManager;
import com.database.managers.JpaUtil;
import com.utils.comparators.StringComparator;
import com.web.containers.EventContainer;

import lombok.Data;


@ManagedBean
@ViewScoped
@Data
public class EventsBean implements Serializable{

	private static final long serialVersionUID = 1L; 
	
	private List<EventContainer> events = new ArrayList<>();
	
	//search status
	private State state = State.ALL;
	private String searchField = "";
	private boolean ascending = true;
	
	
	@PostConstruct
	public void init() {
		events = EventManager.findAll().stream().map(EventContainer::new).collect(Collectors.toList());
		Collections.sort(events,(EventContainer o1, EventContainer o2)->new StringComparator(ascending).compare(o1.getTitle(), o2.getTitle()));
	}
	
	
	
	/**
	 * @return return All the State enum values
	 */
	public State[] getStates() {
		State[] states = new State[4];
		states[0] = State.ALL;
		states[1] = State.RECRUITING;
		states[2] = State.DEVELOPING;
		states[3] = State.FINISHED;
		return states;
	}
	
	
	

	/**
	 * This method will sort projects
	 * @param event
	 */
	public void sort (ValueChangeEvent event) {
		ascending = (boolean) event.getNewValue();
		Collections.sort(events,new Comparator<EventContainer>() {

			@Override
			public int compare(EventContainer o1, EventContainer o2) {
				return new StringComparator(ascending).compare(o1.getTitle(), o2.getTitle());
			}
		});
	}


	/**
	 * 
	 * @param event
	 */
	public void search(ActionEvent event) { 
		search();
	}

	
	
	/**
	 * 
	 * @param event
	 */
	public void search(ValueChangeEvent event) { 
		state = (State) event.getNewValue();
		search();
	}



	/**
	 * This method search projects using searchField and state
	 * It will see if the pattern searchField appears on tags or on title and will compare project state.
	 */
	private void search() {
		String query = "Select distinct e from Event e join e.tags t where ( lower(t) like lower('%"+searchField+"%')"
					 + " or lower(e.title) like lower('%"+searchField+"%') ) ";

		if(state != State.ALL) query += " and e.state = "+state.ordinal();

		events = JpaUtil.executeQuery(query, Event.class)
				.stream()
				.map(EventContainer::new)
				.collect(Collectors.toList());
		
		Collections.sort(events,new Comparator<EventContainer>() {

			@Override
			public int compare(EventContainer o1, EventContainer o2) {
				return new StringComparator(ascending).compare(o1.getTitle(), o2.getTitle());
			}
		});
	}
}
