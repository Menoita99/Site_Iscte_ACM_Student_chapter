package com.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.database.entities.Candidate;
import com.database.entities.Research;
import com.database.managers.JpaUtil;
import com.database.managers.ResearchManager;
import com.web.Session;
import com.web.containers.CandidateContainer;
import com.web.containers.ResearchContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class ResearchBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ResearchContainer research;
	private CandidateContainer candidature;
	
	
	
	
	@PostConstruct
	public void init() {
		String id = Session.getInstance().getRequestMap().get("researchID");

		if(id == null)
			id = "" + Session.getInstance().getSessionAtribute("researchID");	

		try {

			if(id != null && !id.isBlank()) {
				Research r = ResearchManager.findResearch(Integer.parseInt(id));
				if(r != null) {
					research = new ResearchContainer(r);
					ResearchManager.addView(r.getId());

					if(Session.getInstance().getUser() != null) {
						Candidate c = ResearchManager.getCandidature(Session.getInstance().getUser().getId(), research.getId());
						candidature = c == null ? new CandidateContainer() : new CandidateContainer(c);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * Redirects to login page
	 */
	public void redirectToLogin() {
		Session.getInstance().redirectToLogin("research");
		Session.getInstance().setSessionAtribute("researchID",research.getId());
	}
	
	
	
	
	
	
	/**
	 * @return Returns if user in session already was liked this research
	 * if there is no user in session this will return false 
	 */
	public boolean wasLiked() {
		if(Session.getInstance().getUser() == null)
			return false;
		return ResearchManager.wasLiked(research.getId(),Session.getInstance().getUser().getId());
	}





	/**
	 * if there is no user logged in, this method will redirect user to login page.
	 * This method give a "like" to research if user didn't already liked it.
	 * if user already liked the research it will "dislike", 
	 * that means it will remove or add an like object to database.
	 */
	public void likeOrDislikeAction(ActionEvent event) {
		if(Session.getInstance().getUser() == null) { 
			redirectToLogin();
			return;
		}

		if(ResearchManager.wasLiked(research.getId(),Session.getInstance().getUser().getId())) 
			ResearchManager.dislike(research.getId(),Session.getInstance().getUser().getId());
		else
			ResearchManager.like(research.getId(),Session.getInstance().getUser().getId());
		
		research.refresh();
	}
	
	
	
	
	
	/**
	 * Creates or edit candidature
	 */
	public void submitCandidature() {
		if(Session.getInstance().getUser() == null) { 
			redirectToLogin();
			return;
		}
		try {
			if(ResearchManager.getCandidature(Session.getInstance().getUser().getId(), research.getId()) == null) {
				ResearchManager.createCandidate(research.getId(),candidature);
			}
			else {
				Candidate c = ResearchManager.getCandidature(Session.getInstance().getUser().getId(), research.getId());
				c.update(c);
				JpaUtil.mergeEntity(c);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
