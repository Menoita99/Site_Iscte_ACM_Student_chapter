package com.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;

import com.database.managers.ResearchManager;
import com.web.Session;
import com.web.containers.ResearchContainer;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class CreateResearchBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ResearchContainer container;
	private int phase = 1;
	
	private Part uploadedFile;
	private List<Part> uploadedFiles = new ArrayList<>();

	private String tag ="";

	private String usernameOrEmail = "";
	
	
	@PostConstruct
	public void init() {
		UserContainer user = Session.getInstance().getUser();
		if(user == null)
			Session.getInstance().redirectToLogin("createResearch");
		else {
			if(!ResearchManager.isInvestigator(user.getId()))
				Session.getInstance().redirectWithContext("/user");
			else
				container = new ResearchContainer();
		}
	}



	
	
	
	
	/**
	 * Increment phase
	 */
	public void incrementPhase(ActionEvent event) {
		if(phase < 3 )//&& validatePhase2()
			phase++;
	}


	
	
	
	
	/**
	 * Decrement phase
	 */
	public void decrementPhase(ActionEvent event) {
		if(phase > 0 )
			phase--;
	}



	
	
	/**
	 * @param e
	 */
	public void addImage(ValueChangeEvent e) {
		if(e.getNewValue() instanceof Part) {
			uploadedFile = (Part) e.getNewValue();
			uploadedFiles.add(uploadedFile);
			container.getImagePath().add(uploadedFile.getSubmittedFileName());
		}
	}
	
	
	
	
	
	/**
	 * 
	 * @param e
	 */
	public void removeImage(ActionEvent e) {
		String fileName = (String) e.getComponent().getAttributes().get("fileName");
		container.getImagePath().remove(fileName);
		for (Part part : uploadedFiles) {
			if(part.getSubmittedFileName().equals(fileName)) {
				uploadedFiles.remove(part);
				return;
			}
		}
	}
	
	
	
	
	
	/**
	 * 
	 */
	public void addTag(ActionEvent event) {
		if(!tag.isBlank()) {
			if(!container.getTags().contains(tag)) {
				container.getTags().add(tag);
				tag = "";	
			}
		}
	}


	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void removeTag(ActionEvent event ) {
		String tag = (String) event.getComponent().getAttributes().get("tag");
		container.getTags().remove(tag);
	}
}
