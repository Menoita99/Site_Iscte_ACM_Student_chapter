package com.containers.objects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.database.entities.Event;
import com.database.entities.EventInfo;
import com.database.entities.User;
import com.database.managers.EventManager;
import com.utils.ArrayUtil;

public class EventContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String description;

	private int vacancies;
	private int vacanciesFilled;

	private int manager;

	private List<String> images;
	private List<EventInfo> infos = new ArrayList<>();
	private List<UserContainer> staff = new ArrayList<>();





	/**
	 * Constructor that uses an event 
	 */
	public EventContainer(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.description = event.getDescription();
		this.vacancies = event.getVacancies();
		this.images = event.getImagePath();
		this.manager = event.getManager().getId();
		this.vacanciesFilled = (int)EventManager.getOccupation(id);

		infos = sortByDate(EventManager.getEventInfos(id));
	}





	/**
	 * Constructor that uses an event id 
	 */
	public EventContainer(int id) {
		this.id = id;

		Event event = EventManager.getEventById(id);

		this.title = event.getTitle();
		this.description = event.getDescription();
		this.vacancies = event.getVacancies();
		this.images = event.getImagePath();
		this.manager = event.getManager().getId();
		this.vacanciesFilled = (int)EventManager.getOccupation(id);

		infos = sortByDate(EventManager.getEventInfos(id));
	}





	/**
	 * Sorts a List of EventInfos by near future date and then by past near date
	 * @param eventInfos
	 * @return sorted list
	 */
	public static List<EventInfo> sortByDate(List<EventInfo> eventInfos) {
		EventInfo[] futureArray = new EventInfo[eventInfos.size()];
		EventInfo[] pastArray = new EventInfo[eventInfos.size()];

		LocalDateTime now = LocalDateTime.now().withNano(0);

		for (EventInfo ei :eventInfos) {			

			if(ei.getStartDate().isAfter(now)) {			//checks for future Events

				boolean flag = false;

				for(int i = 0; !flag && i < futureArray.length;i++) {		//iterates the array to compare dates

					if(futureArray[i] == null) {									
						futureArray[i] = ei;
						flag = true;
					}else if(futureArray[i].getStartDate().isAfter(ei.getStartDate())) {
						ArrayUtil.shiftRight(futureArray,i);
						futureArray[i] = ei;
						flag = true;
					}
				}
				
			}else {										//In case event is in the past

				boolean flag = false;

				for(int i = 0; !flag && i < pastArray.length;i++) {		//iterates the array to compare dates
					
					if(pastArray[i] == null) {									
						pastArray[i] = ei;
						flag = true;
					}else if(pastArray[i].getStartDate().isBefore(ei.getStartDate())) {
						ArrayUtil.shiftRight(pastArray,i);
						pastArray[i] = ei;
						flag = true;
					}

				}
			}
		}

		List<EventInfo> output = new ArrayList<>();

		for (Object eventInfo : ArrayUtil.fusion(futureArray,pastArray)) 
			if(eventInfo != null)
				output.add((EventInfo)eventInfo);

		return output;
	}






	/**
	 * Get the event likes
	 */
	public long getLikes() {
		return EventManager.getLikes(id);
	}





	/**
	 * 
	 * @param index index on arrayList
	 * @return return the image path that is the position index 
	 * if index is bigger then the images size or is less then zero it returns null
	 */
	public String getImage(int index) {
		return index<images.size() && index>=0 ? images.get(index) : null;
	}






	/**
	 * @return return the participants
	 */
	public List<UserContainer> getStaff() {
		if(staff.isEmpty()) {
			staff = new ArrayList<>();
			for(User u: EventManager.getStaffParticipants(id))
				staff.add(UserContainer.convertToUserContainer(u));
		}
		return staff;
	}



	
	
	
	/**
	 * Used to get sooner eventInfo place
	 * @return return a string with the address
	 */
	public String getSoonerPlace() {
		return infos.get(0).getPlace();
	}

		
	
	
	
	
	/**
	 * Used to get sooner eventInfo place
	 * @return return a string with the address
	 */
	public String getSoonerDate() {
		LocalDateTime time = infos.get(0).getStartDate();
		return time.getYear() + "/" + time.getMonthValue() + "/" + time.getDayOfMonth() + " - " + time.getDayOfWeek();
	}

	
	
	
	
//-----------------------GETTERS AND SETTERS

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}





	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}





	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}





	/**
	 * @return the vacancies
	 */
	public int getVacancies() {
		return vacancies;
	}





	/**
	 * @return the vacanciesFilled
	 */
	public int getVacanciesFilled() {
		return vacanciesFilled;
	}





	/**
	 * @return the manager
	 */
	public int getManager() {
		return manager;
	}





	/**
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}





	/**
	 * @return the infos
	 */
	public List<EventInfo> getInfos() {
		return infos;
	}





	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @param vacancies the vacancies to set
	 */
	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
	}





	/**
	 * @param vacanciesFilled the vacanciesFilled to set
	 */
	public void setVacanciesFilled(int vacanciesFilled) {
		this.vacanciesFilled = vacanciesFilled;
	}





	/**
	 * @param manager the manager to set
	 */
	public void setManager(int manager) {
		this.manager = manager;
	}





	/**
	 * @param images the images to set
	 */
	public void setImages(List<String> images) {
		this.images = images;
	}





	/**
	 * @param infos the infos to set
	 */
	public void setInfos(List<EventInfo> infos) {
		this.infos = infos;
	}





	/**
	 * @param staff the staff to set
	 */
	public void setStaff(List<UserContainer> staff) {
		this.staff = staff;
	}





	@Override
	public String toString() {
		return "EventContainer [id=" + id
				+ ", title=" + title 
				+ ", description=" + description 
				+ ", vacancies=" + vacancies
				+ ", image=" + images 
				+ ", manager=" + manager + "]";
	}


	
	
	
	//to debug
	public static void main(String[] args) {
		List<EventInfo> ei = new ArrayList<EventInfo>();

		for(int i = 0 ; i<10 ; i++) {
			EventInfo e = new EventInfo();
			e.setStartDate(LocalDateTime.of(2014+(int)(Math.random()*10), Month.JANUARY, 1, 10, 10, 30));
			ei.add(e);
		}

		List<EventInfo> ei1 = sortByDate(ei);

		System.out.println("NÃO ORDENADO           |    ORDENADO");
		for(int i = 0 ; i<10 ; i++) 
			System.out.println(ei.get(i).getStartDate()+"    |    "+ei1.get(i).getStartDate());
	}
}
