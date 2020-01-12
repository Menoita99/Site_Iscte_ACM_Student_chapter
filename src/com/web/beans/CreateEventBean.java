package com.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import com.database.entities.User;
import com.database.managers.EventManager;
import com.database.managers.UserManager;
import com.utils.FileManager;
import com.utils.IllegalAssertionException;
import com.web.Session;
import com.web.containers.UserContainer;

@ManagedBean
@ViewScoped
public class CreateEventBean implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;
	private String requirements;
	private String vacancies;
	private String observation;
	private String budget = "0";

	//STORAGE ATTRIBUTES
	private List<Part> images = new ArrayList<>();
	private List<String> staff = new ArrayList<>();
	private List<LocalDateTime> dates = new ArrayList<>();
	private List<String> places = new ArrayList<>(); 
	private List<String> durations = new ArrayList<>();
	private List<String> tags = new ArrayList<>();

	//TEMP ATTRIBUTES
	private Part file;
	private String usernameOrEmail;
	private String place;
	private String duration;
	private String tag;
	private String date;

	private String errorMessage;

	private int stage = 1;





	/**
	 * Redirects to login page
	 */
	public void redirectToLogin() {
		Session.getInstance().redirectWithContext("/login");
	}






	/**
	 * this method verifies stage inputs, and change stage if everything is valid
	 * otherwise displays the error message correspondent.
	 */
	public void nextStage() {
		setErrorMessage(null);

		if (stage == 1) {
			validateStage1();
		} else if (stage == 2) {
			executeStage2();
		} else if(stage >3){
			setErrorMessage("Something went wrong!");
			System.out.println("(CreateEventBean)[nextStage()] Stage value --> "+stage);
		}
		System.out.println("nextStage was called, stage -> "+stage);
	}





	/**
	 * Validates Stage 2 form
	 * Creates an event with state equals ON_APPROVAL
	 * send's an email to administrators warning about the new submit
	 */
	private void executeStage2() {
		
		if(dates.isEmpty() || durations.isEmpty() || places.isEmpty()) {
			setErrorMessage("Event must have at least one date");
		
		}else {
			try {
				List<String> imagePaths = FileManager.saveEventImages(images);
				EventManager.createEvent(Integer.parseInt(vacancies), title, description, imagePaths, Session.getInstance().getUser().getId(),
										tags, requirements, observation, Double.parseDouble(budget), staff, dates, places, durations);

				stage++;
			} catch (IOException | NumberFormatException e) {
				setErrorMessage("Something went wrong please, try again");
				e.printStackTrace();
			} catch (IllegalAssertionException e) {
				setErrorMessage(e.getMessage());
				e.printStackTrace();
			}
		}
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
			stage++;
		}else 
			setErrorMessage("Invalid title");
	}





	/**
	 * This method verifies if usernameOrEmail was a valid user to be added
	 * and in case it has it adds to staff list 
	 */
	public void addUser() {
		setErrorMessage(null);
		UserContainer loggedUser = Session.getInstance().getUser();

		if(usernameOrEmail != null && loggedUser!=null  &&  !usernameOrEmail.isBlank()) {								//verify if user isn't a blank string
			if(!(usernameOrEmail.equals(loggedUser.getUsername()) || usernameOrEmail.equals(loggedUser.getEmail()))) {	//verify if user is the manager 

				User check = UserManager.getUserByEmail(usernameOrEmail);
				if(check == null) check = UserManager.getUserByUsername(usernameOrEmail);

				if(check != null && !staff.contains(check.getUsername())) {												//verifies if user exists
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
		setErrorMessage(null);
		if(file != null) {
			images.add(file);
			file = null;
		}
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
	 * Adds tag to tags and cleans file input field
	 */
	public void addTag() {
		setErrorMessage(null);
		if(tag != null) 
			if(!tag.isBlank()) 
				if(!tags.contains(tag)) {
					tags.add(tag);
					tag = null;
				}
	}




	/**
	 * @param deleteTag tag to be deleted
	 * remove tag from tags
	 */
	public void deleteTag(String deleteTag) {
		if(deleteTag != null) 
			if(!deleteTag.isBlank()) 
				if(!tags.contains(deleteTag)) 
					tags.remove(tag);
	}





	/**
	 * Adds information into
	 *  places
	 *  durations 
	 *  dates
	 * This method is validated by xhtml validators
	 */
	public void addInfo() {
		setErrorMessage(null);

		if(duration != null && date != null && place != null && !duration.isBlank() && !date.isBlank() && !place.isBlank()) {			//verify if fields are empty
			try {
				
				LocalDateTime localDate = LocalDateTime.parse(date);
				
				if(!dates.contains(localDate))
					dates.add(localDate);
				
				if(!durations.contains(duration))
					durations.add(duration);
				
				if(!places.contains(place)) 
					places.add(place);
				
			} catch (Exception e) {
				setErrorMessage("Something went worng");
				e.printStackTrace();
			}
		}else 
			setErrorMessage("Required fields can't be empty");
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
		return observation;
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
	 * @return the places
	 */
	public List<String> getPlaces() {
		return places;
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
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}





	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}





	/**
	 * @return the dates
	 */
	public List<LocalDateTime> getDates() {
		return dates;
	}






	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}






	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		System.out.println(date);
		this.date = date;
	}






	/**
	 * @param dates the dates to set
	 */
	public void setDates(List<LocalDateTime> dates) {
		this.dates = dates;
	}






	/**
	 * @param tags the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}





	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
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
	 * @param places the places to set
	 */
	public void setPlaces(List<String> places) {
		this.places = places;
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
		this.observation = observation;
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
