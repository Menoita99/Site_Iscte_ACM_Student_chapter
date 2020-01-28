package com.web.containers;

import java.io.Serializable;

import com.database.entities.Investigator;

import lombok.Data;

@Data
public class InvestigatorContainer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private UserContainer user;
	private String institution;
	
	
	public InvestigatorContainer(Investigator in) {
		this.id= in.getId();
		this.user =  new UserContainer(in.getUser());
		this.institution = in.getInstitution();
	}
}
