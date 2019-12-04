package com.web.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import com.database.entities.User;
import com.database.managers.EventManager;
import com.database.managers.UserManager;
import com.web.Session;
import com.web.containers.UserContainer;

@ManagedBean
@ViewScoped
public class CreateEventBean {


	private String title;
	private String description;
	private String requirements;
	private String vacancies;
	private String Observation;
	private String budget = "0";

	//STORAGE ATTRIBUTES
	private List<Part> images = new ArrayList<>();
	private List<String> staff = new ArrayList<>();
	private List<LocalDateTime> schedules = new ArrayList<>();
	private List<String> places = new ArrayList<>();
	private List<String> durations = new ArrayList<>();

	//TEMP ATTRIBUTES
	private Part file;
	private String usernameOrEmail;
	private String time;
	private String place;
	private String duration;
	
	private String errorMessage;

	private int stage = 1;






	/**
	 * this method verifies stage inputs, and change stage if everything is valid
	 * otherwise displays the error message correspondent.
	 */
	public void nextStage() {
		setErrorMessage(null);

		switch (stage) {
		case 1:			//stage 1
			validateStage1();
			break ;
		case 2:
			validateStage2();
			break ;
		case 3:
			createEvent();
			break ;
		default:
			setErrorMessage("Something went wrong!");
			System.out.println("(CreateEventBean)[nextStage()] Stage value --> "+stage);
		}
		System.out.println("nextStage was called, stage -> "+stage);
	}




	
	/**
	 * Creates an event with state equals ON_APPROVAL
	 * send's an email to administrators warning about the new submit
	 */
	private void createEvent() {
		// TODO Auto-generated method stub
		
	}





	/**
	 * Validates Stage 2 form
	 */
	private void validateStage2() {
		// TODO Auto-generated method stub
		stage++;
	}





	/**
	 * Validates Stage 1 form
	 */
	private void validateStage1() {

		if(title != null && !title.isBlank()) {										
			if(EventManager.getEventByTitle(title) != null)  				//checks if title is already in use
				setErrorMessage("There is already an event with the given title");
			else {
				try {
					Integer.parseInt(vacancies);								//checks if vacancies is a valid number
				}catch (NumberFormatException e) {
					setErrorMessage("Invalid number at Vacancies");
				}
			}
		}else 
			setErrorMessage("Invalid title");
		stage++;															//changes stage
	}





	/**
	 * This method removes the part given from the images list 
	 * @param part part to be deleted
	 */
	public void deleteImage(Part part) {
		if( part != null) 
			images.remove(part);
	}





	/**
	 * This method verifies if usernameOrEmail was a valid user to be added
	 * and in case it has it adds to staff list 
	 * 
	 */
	public void addUser() {
		setErrorMessage(null);
		UserContainer loggedUser = Session.getInstance().getUser();

		if(usernameOrEmail != null && loggedUser!=null) {
			if(!usernameOrEmail.isBlank() && 
					!(usernameOrEmail.equals(loggedUser.getUsername()) || usernameOrEmail.equals(loggedUser.getEmail()))) {	//verifies if user is the manager and if is not a blank string

				User check = UserManager.getUserByEmail(usernameOrEmail);
				if(check == null) check = UserManager.getUserByUsername(usernameOrEmail);

				if(check != null && !staff.contains(check.getUsername())) {						//verifies if user exists
					staff.add(check.getUsername());
					usernameOrEmail = null;

				}else
					setErrorMessage("User does not exist");
			}else
				setErrorMessage("This user is the event manager, don't need to be added");
		}
	}





	/**
	 * This method removes the part given from the images list 
	 * @param part part to be deleted
	 */
	public void deleteUser(String username) {
		if(username != null ) 
			if(staff.contains(username))
				staff.remove(username);
	}





	/**
	 * Adds file to images and cleans file input field
	 */
	public void addImage() {
		if(file != null) {
			images.add(file);
			file = null;
		}
	}





	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}





	/**
	 * @return the requirements
	 */
	public String getRequirements() {
		return requirements;
	}





	/**
	 * @return the budget
	 */
	public String getBudget() {
		return budget;
	}





	/**
	 * @return the stage
	 */
	public int getStage() {
		return stage;
	}





	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}





	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}





	/**
	 * @return the vacancies
	 */
	public String getVacancies() {
		return vacancies;
	}





	/**
	 * @return the images
	 */
	public List<Part> getImages() {
		return images;
	}





	/**
	 * @return the staff
	 */
	public List<String> getStaff() {
		return staff;
	}





	/**
	 * @return the observation
	 */
	public String getObservation() {
		return Observation;
	}





	/**
	 * @return the file
	 */
	public Part getFile() {
		return file;
	}





	/**
	 * @return the usernameOrEmail
	 */
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}





	/**
	 * @return the schedules
	 */
	public List<LocalDateTime> getSchedules() {
		return schedules;
	}





	/**
	 * @return the places
	 */
	public List<String> getPlaces() {
		return places;
	}





	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}





	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}





	/**
	 * @return the durations
	 */
	public List<String> getDurations() {
		return durations;
	}





	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}





	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}





	/**
	 * @param durations the durations to set
	 */
	public void setDurations(List<String> durations) {
		this.durations = durations;
	}





	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}





	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}





	/**
	 * @param places the places to set
	 */
	public void setPlaces(List<String> places) {
		this.places = places;
	}





	/**
	 * @param schedules the schedules to set
	 */
	public void setSchedules(List<LocalDateTime> schedules) {
		this.schedules = schedules;
	}





	/**
	 * @param usernameOrEmail the usernameOrEmail to set
	 */
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}





	/**
	 * @param file the file to set
	 */
	public void setFile(Part file) {
		this.file = file;
	}





	/**
	 * @param observation the observation to set
	 */
	public void setObservation(String observation) {
		Observation = observation;
	}





	/**
	 * @param images the images to set
	 */
	public void setImages(List<Part> images) {
		this.images = images;
	}





	/**
	 * @param vacancies the vacancies to set
	 */
	public void setVacancies(String vacancies) {
		this.vacancies = vacancies;
	}





	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}





	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}





	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}





	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}





	/**
	 * @param budget the budget to set
	 */
	public void setBudget(String budget) {
		this.budget = budget;
	}





	/**
	 * @param stage the stage to set
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
}
