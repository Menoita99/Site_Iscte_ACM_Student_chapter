package com.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Part;

import com.database.entities.Investigator;
import com.database.entities.ResearchType;
import com.database.entities.User;
import com.database.managers.ResearchManager;
import com.database.managers.UserManager;
import com.web.Session;
import com.web.containers.InvestigatorContainer;
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
	private String link ="";
	private String institutions ="";
	private String usernameOrEmail = "";
	
	
	
	
	
	@PostConstruct
	public void init() {
		UserContainer user = Session.getInstance().getUser();
		if(user == null)
			Session.getInstance().redirectToLogin("createResearch");
		else {
			Investigator inv = ResearchManager.findInvestigatorByUserId(user.getId());
			if(inv == null)
				Session.getInstance().redirectWithContext("/unauthorizationError");
			else
				container = new ResearchContainer(new InvestigatorContainer(inv));
		}
	}



	
	/**
	 * @return
	 */
	public ResearchType[] getTypes() {
		return ResearchType.values();
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
	 * Add tag to research
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
	 * @param event
	 */
	public void removeTag(ActionEvent event ) {
		String tag = (String) event.getComponent().getAttributes().get("tag");
		container.getTags().remove(tag);
	}
	
	
	
	
	
	/**
	 * Add tag to research
	 */
	public void addInstitutions(ActionEvent event) {
		if(!institutions.isBlank()) {
			if(!container.getInstitutions().contains(institutions)) {
				container.getInstitutions().add(institutions);
				institutions = "";	
			}
		}
	}


	
	
	
	
	/**
	 * @param event
	 */
	public void removeInstitutions(ActionEvent event ) {
		String institution = (String) event.getComponent().getAttributes().get("institution");
		container.getInstitutions().remove(institution);
	}
	
	
	
	
	
	/**
	 * Add tag to research
	 */
	public void addlink(ActionEvent event) {
		if(!link.isBlank()) {
			if(!container.getUsefulllinks().contains(link)) {
				container.getUsefulllinks().add(link);
				link = "";	
			}
		}
	}


	
	
	
	
	/**
	 * @param event
	 */
	public void removeLink(ActionEvent event ) {
		String link = (String) event.getComponent().getAttributes().get("link");
		container.getUsefulllinks().remove(link);
	}
	
	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void addInvestigator(ActionEvent event) {
		if(!usernameOrEmail.isBlank()) {
				
				User u = UserManager.getUserByUsername(usernameOrEmail); //check if there is an user with username 
				if(u == null) 
					u = UserManager.getUserByEmail(usernameOrEmail);	//check if there is an user with email ( if didn't found one with username )

				if(u != null) {
					Investigator invs = ResearchManager.findInvestigator(u.getId());
					if(invs != null) {
						
						InvestigatorContainer inv = new InvestigatorContainer(invs);
						if(!container.getInvestigators().contains(inv))
							container.getInvestigators().add(inv);
						else
							sendMessageToComponent("investigatorForm:username", "User already added");//
	
						usernameOrEmail = "";
					}else
						sendMessageToComponent("investigatorForm:username", "User isn't an investigator");
				}else 
					sendMessageToComponent("investigatorForm:username", "User does not exist");
		}
	
	}


	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void removeInvestigator(ActionEvent event) {
		InvestigatorContainer inv =  (InvestigatorContainer) event.getComponent().getAttributes().get("investigator");
		if(inv.getUser().getId() != Session.getInstance().getUser().getId())
			container.getInvestigators().remove(inv);
		else
			sendMessageToComponent("investigatorForm:username", "Can't remove the manager");
	}

	
	
	
	
	/**
	 * Sends a message to component
	 * 
	 * @param componentId
	 * @param message
	 */
	private void sendMessageToComponent(String componentId, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(componentId, msg);
	}
}
