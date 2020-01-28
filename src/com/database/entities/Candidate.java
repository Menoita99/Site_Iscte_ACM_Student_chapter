package com.database.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.database.managers.UserManager;
import com.web.containers.CandidateContainer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

/**
 * Entity implementation class for Entity: Candidate
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Candidate implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Exclude
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_CANDIDATE_ID"), nullable= false)
	private User user;
	
	
	@Column(length = 665, nullable = false)
	private String motivation;
	
	
	@Column(length = 665, nullable = false)
	private String hablitations;
	
	private String cv;	
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date(System.currentTimeMillis());
   
	
	public Candidate(CandidateContainer candidature) {
		this.motivation = candidature.getMotivation();
		this.user = UserManager.getUserById(candidature.getUser().getId());
		this.hablitations = candidature.getHablitations();
		this.cv = user.getCv();
	}


	public void update(Candidate candidature) {
		this.motivation = candidature.getMotivation();
		this.user = UserManager.getUserById(candidature.getUser().getId());
		this.hablitations = candidature.getHablitations();
		this.cv = user.getCv();
	}
}
