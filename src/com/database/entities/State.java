package com.database.entities;

import java.util.List;

public enum State {
	
	RECRUITING, DEVELOPING, FINISHED, APPROVED, ALL, CANCELED, ON_APPROVAL, DECLINED;
	
	/**
	 * @return return states of acceptance
	 */
	public static List<State> getAcceptanceStates(){
		return List.of(State.RECRUITING,State.DEVELOPING,State.FINISHED,State.APPROVED);
	}
}
