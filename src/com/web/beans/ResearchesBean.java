package com.web.beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.entities.State;
import com.database.managers.ResearchManager;
import com.utils.comparators.StringComparator;
import com.web.containers.ResearchContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class ResearchesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	List<ResearchContainer> researches;
	

	//Search status
	private State state = State.ALL;
	private String searchField = "";
	private boolean ascending = true;
	
	
	
	
	@PostConstruct
	public void init() {
		researches = ResearchManager.findAllResearches().stream().map(ResearchContainer::new).collect(Collectors.toList());
		Collections.sort(researches,(ResearchContainer o1, ResearchContainer o2) -> new StringComparator().compare(o1.getTitle(),o2.getTitle()));
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
	
	
	
	
	
}
