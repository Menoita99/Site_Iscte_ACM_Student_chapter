package com.database.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProjectRespectID
 *
 */
@Embeddable
public class ProjectRespectID implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private int userID;
	
	@Column(nullable = false)
	private int projectID;

	@Override
	public int hashCode() {
		return Objects.hash(projectID, userID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProjectRespectID))
			return false;
		ProjectRespectID other = (ProjectRespectID) obj;
		return projectID == other.projectID && userID == other.userID;
	}

	@Override
	public String toString() {
		return "ProjectRespectID [userID=" + userID + ", projectID=" + projectID + "]";
	}
}
