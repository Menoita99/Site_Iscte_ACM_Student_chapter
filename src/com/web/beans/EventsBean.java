package com.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.database.entities.State;
import com.database.managers.EventManager;
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
	
	
	public EventsBean() {
		events = EventManager.findAll().stream().map(EventContainer::new).collect(Collectors.toList());
		//comparador
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
//		ascending = (boolean) event.getNewValue();
//		Collections.sort(projects, new ProjectTimeComparator(true,ascending));
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
//		state = (State) event.getNewValue();
//		search();
	}



	/**
	 * This method search projects using searchField and state
	 * It will see if the pattern searchField appears on tags or on title and will compare project state.
	 */
	private void search() {
//		String query = "Select distinct p from Project p join p.tags t where ( t like '%"+searchField+"%' or p.title like '%"+searchField+"%' ) ";
//
//		if(state != State.ALL) query += " and p.state = "+state.ordinal();
//
//		projects = JpaUtil.executeQuery(query, Project.class)
//				.stream()
//				.map(ProjectContainer::new)
//				.collect(Collectors.toList());
//		
//		Collections.sort(projects, new ProjectTimeComparator(true,ascending));
	}
}
