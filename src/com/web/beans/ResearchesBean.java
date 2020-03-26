package com.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.database.entities.Research;
import com.database.entities.State;
import com.database.managers.JpaUtil;
import com.database.managers.ResearchManager;
import com.utils.comparators.StringComparator;
import com.web.containers.ResearchContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class ResearchesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ResearchContainer> researches;

	private int page = 1;
	private int rpp = 12;//research per page

	//Search status
	private State state = State.ALL;
	private String searchField = "";
	private boolean ascending = true;




	@PostConstruct
	public void init() {
		researches = ResearchManager.findAllResearches().stream().map(ResearchContainer::new).collect(Collectors.toList());
		Collections.sort(researches,(ResearchContainer o1, ResearchContainer o2) -> new StringComparator(ascending).compare(o1.getTitle(),o2.getTitle()));
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
		Collections.sort(researches, (ResearchContainer o1 ,ResearchContainer o2 )-> new StringComparator(ascending).compare(o1.getTitle(), o2.getTitle()));
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
		String query = "Select distinct r from Research r join r.tags t where ( t like '%"+searchField+"%' or r.title like '%"+searchField+"%' ) ";

		if(state != State.ALL) query += " and r.state = "+state.ordinal();

		researches = JpaUtil.executeQuery(query, Research.class)
				.stream()
				.map(ResearchContainer::new)
				.collect(Collectors.toList());

		Collections.sort(researches, (ResearchContainer o1 ,ResearchContainer o2 )-> new StringComparator(ascending).compare(o1.getTitle(), o2.getTitle()));
	}







	/**
	 * @return returns the researchContainer that are present in
	 */
	public List<ResearchContainer> getResearches(){
		List<ResearchContainer> outPut = new ArrayList<>();
		if(page>0) {
			int start = Math.min(rpp*page-rpp, researches.size());
			int finish = Math.min(rpp*page, researches.size());
			for(int i = start; i< finish ; i++) 
				outPut.add(researches.get(i));
		}
		return outPut;
	}




	/**
	 * @return the number of total pages that research list has
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double)researches.size()/(double)rpp);
	}

}
