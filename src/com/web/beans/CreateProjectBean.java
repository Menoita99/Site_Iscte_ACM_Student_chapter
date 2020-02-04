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

import com.database.entities.Material;
import com.database.entities.Project;
import com.database.entities.User;
import com.database.managers.JpaUtil;
import com.database.managers.UserManager;
import com.utils.FileManager;
import com.web.Session;
import com.web.containers.ProjectContainer;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class CreateProjectBean implements Serializable{

	private static final long serialVersionUID = 1L; 

	private ProjectContainer container;
	private int phase = 1;

	private Part uploadedFile;
	private List<Part> uploadedFiles = new ArrayList<>();

	private String tag ="";

	private String usernameOrEmail = "";

	private Material material = new Material();


	@PostConstruct
	public void init() {
		UserContainer user = Session.getInstance().getUser();
		if(user == null)
			Session.getInstance().redirectToLogin("createProject");
		else {
			container  = new ProjectContainer(user);
		}
	}



	
	
	
	
	/**
	 * Increment phase
	 */
	public void incrementPhase(ActionEvent event) {
		if(phase < 3 && validatePhase2())
			phase++;
	}


	
	
	
	
	/**
	 * Validates all required components on phase2
	 */
	private boolean validatePhase2() {
		if(phase == 2) {
			boolean valid = true;

			if(container.getDeadline() == null ) {
				sendMessageToComponent("dateForm:deadline", "This field must not be empty");
				valid = false;
			}
			if(container.getSubscriptionDeadline() == null) {
				sendMessageToComponent("dateForm:sdeadline", "This field must not be empty");
				valid = false;
			}
			if(container.getDeadline() != null && container.getSubscriptionDeadline() != null) {
				if(container.getDeadline().before(container.getSubscriptionDeadline())) {
					sendMessageToComponent("dateForm:sdeadline", "Deadline must be after subscription deadline");
					valid = false;
				}
			}
			return valid;
		}
		return true;
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


	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void addUser(ActionEvent event) {
		if(!usernameOrEmail.isBlank()) {
			if(container.getParticipants().size()<container.getMaxMembers()) {
				
				User u = UserManager.getUserByUsername(usernameOrEmail); //check if there is an user with username 
				if(u == null) 
					u = UserManager.getUserByEmail(usernameOrEmail);	//check if there is an user with email ( if didn't found one with username )

				if(u != null) {

					UserContainer participant = new UserContainer(u);
					if(!container.getParticipants().contains(participant))
						container.getParticipants().add(participant);
					else
						sendMessageToComponent("participantForm:username", "User already added");//

					usernameOrEmail = "";
				}else 
					sendMessageToComponent("participantForm:username", "User does not exist");
			}else
				sendMessageToComponent("participantForm:username", "Maximum number of members reached");
		}
	}


	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void removeUser(ActionEvent event) {
		UserContainer user =  (UserContainer) event.getComponent().getAttributes().get("participant");
		if(!user.equals(container.getManager()))
			container.getParticipants().remove(user);
		else
			sendMessageToComponent("participantForm:username", "Can't remove the manager");
	}


	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void addMaterial(ActionEvent event) {
		if(!container.getMaterial().contains(material)) {
			container.getMaterial().add(material);
			material = new Material();
		}
	}


	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void removeMaterial(ActionEvent event) {
		Material m = (Material) event.getComponent().getAttributes().get("material");
		container.getMaterial().remove(m);
	}


	
	
	
	
	/**
	 * 
	 * @return
	 */
	public String getTotal() {
		double total = 0.00;
		if(!container.getMaterial().isEmpty()) {
			total = 0;
			for (Material m : container.getMaterial()) 
				total += (m.getPrice()*m.getQuantity());
		}
		return total+"";
	}


	
	
	
	
	/**
	 * Saves project into database
	 * @param event
	 */
	public String submitProject() {
		List<String> paths = FileManager.saveProjectFiles(uploadedFiles);
		container.setImagePath(paths);
		JpaUtil.createEntity(new Project(container));
		return  "user?rendered=projects";
	}
}
