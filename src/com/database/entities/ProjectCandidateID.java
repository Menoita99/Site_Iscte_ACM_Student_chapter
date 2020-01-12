package com.database.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class ProjectCandidateID implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private int userID;

	@Column(nullable = false)
	private int projectID;
}
