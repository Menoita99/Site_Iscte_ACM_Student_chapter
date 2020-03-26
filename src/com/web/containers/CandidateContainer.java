package com.web.containers;

import java.io.Serializable;
import java.util.Date;

import com.database.entities.Candidate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Data
@NoArgsConstructor
public class CandidateContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	private UserContainer user;
	
	private String motivation;
	private String hablitations;
	private Date date;
	
	private int projectId = 0;
	private int eventId = 0;
	private int researchId = 0;
	
	/**
	 * Constructor
	 */
	public CandidateContainer(Candidate p) {
		this.user = new UserContainer(p.getUser());
		this.motivation = p.getMotivation();
		this.hablitations = p.getHablitations();
		this.date = p.getDate();
	}
}
