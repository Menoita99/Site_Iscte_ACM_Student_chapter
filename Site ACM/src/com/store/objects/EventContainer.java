package com.store.objects;

import java.io.Serializable;

public class EventContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String description;
	private int vacancies;
	private String image;
	private int manager;
	private String place;

	public EventContainer(int id, String title, String description, int vacancies, String image, int manager, String place) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.vacancies = vacancies;
		this.image = image;
		this.manager = manager;
		this.place = place;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public int getVacancies() {
		return vacancies;
	}

	public String getImage() {
		return image;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "EventContainer [id=" + id + ", title=" + title + ", description=" + description + ", vacancies=" + vacancies
				+ ", image=" + image + ", manager=" + manager + ", place=" + place + "]";
	}

	

}
