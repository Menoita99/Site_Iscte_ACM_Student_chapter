package com.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import com.database.managers.EventManager;

@ManagedBean
@RequestScoped
public class CreateEventBean {


	private String title;
	private String description;
	private String requirements;
	private String vacancies;

	
	private List<Part> images = new ArrayList<>();
	private List<String> staff = new ArrayList<>();
	
	private String Observation;
	private String budget = "0";
	
	private String errorMessage;

	private int stage = 1;





	/**
	 * this method verifies stage inputs, and change stage if everything is valid
	 * otherwise displays the error message correspondent.
	 */
	public void nextStage() {
		switch (stage) {

		case 1:			//stage 1

			if(title != null && !title.isBlank()) {										
				if(EventManager.getEventByTitle(title) != null) {				//checks if title is already in use
					setErrorMessage("Title already exists");
					break;
				}
				
				try {
					Integer.parseInt(vacancies);								//checks if vacancies is a valid number
				}catch (NumberFormatException e) {
					setErrorMessage("Invalid number at Vacancies");
				}

			}else {
				setErrorMessage("Invalid title");
				break;			
			}
		
			setErrorMessage(null);												//cleans error message to next stage
			stage++;															//changes stage

			break ;

		case 2:
			
			System.out.println(images);
			
			try {
				Double.parseDouble(budget);										//checks if budget is a valid number
			}catch (NumberFormatException e) {
				setErrorMessage("Budget isn't a valid number");
			}

			break ;
		case 3:

			break ;

		default:
			setErrorMessage("Something went wrong!");
			System.out.println("(CreateEventBean)[nextStage()] Stage value --> "+stage);
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
	 * @param observation the observation to set
	 */
	public void setObservation(String observation) {
		Observation = observation;
	}










	/**
	 * @param staff the staff to set
	 */
	public void setStaff(String staff) {
		this.staff.add(staff);
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
