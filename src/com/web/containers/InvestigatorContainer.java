package com.web.containers;

import java.io.Serializable;

import com.database.entities.ResearchType;

import lombok.Data;

@Data
public class InvestigatorContainer implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private UserContainer user;
	private String institution;
	private ResearchType type;
	
}
