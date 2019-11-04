package com.store.objects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.database.entities.Event;
import com.database.entities.EventInfo;
import com.database.managers.EventManager;

public class EventContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String description;
	private int vacancies;
	private String image;
	private int manager;

	private List<EventInfo> infos = new ArrayList<>();




	public EventContainer(int id, String title, String description, int vacancies, String image, int manager) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.vacancies = vacancies;
		this.image = image;
		this.manager = manager;

		infos = EventManager.getEventInfos(id);
	}




	/**
	 *if there is future event dates then this will return the sooner date 
	 *otherwise it will return the sooner past date
	 */
	private EventInfo soonerDate() {
		if(!infos.isEmpty()) {
			EventInfo sooner = infos.get(0);

			LocalDateTime today = LocalDateTime.now().withNano(0);

			List<EventInfo> future = new ArrayList<EventInfo>();

			for (EventInfo eventInfo : infos) 				//takes all future dates
				if(eventInfo.getStartDate().isAfter(today))
					future.add(eventInfo);


			if(future.isEmpty()) {							//if no future dates
				for (EventInfo eventInfo : infos) {
					if(Math.abs(eventInfo.getStartDate().until(today,ChronoUnit.SECONDS)) <
							Math.abs(sooner.getStartDate().until(today,ChronoUnit.SECONDS)))
						sooner = eventInfo;
				}
				return sooner;

			}else {
				sooner = future.get(0);

				for (EventInfo futureInfo : future) {
					if(Math.abs(futureInfo.getStartDate().until(today,ChronoUnit.SECONDS)) <
							Math.abs(sooner.getStartDate().until(today,ChronoUnit.SECONDS)))
						sooner = futureInfo;
				}
				return sooner;
			}
		}
		return null;
	}




	/**
	 * @return the date as a string of the next event
	 */
	public String getSoonerDate() {
		if(soonerDate() == null) return "Null data";
		LocalDateTime date = soonerDate().getStartDate();
		return date.getYear()+"/"+date.getMonthValue()+"/"+date.getDayOfMonth()+" "+date.getDayOfWeek().toString().toLowerCase() ;
	}


	/**
	 * @return the place of the next event
	 */
	public String getSoonerPlace() {
		EventInfo eInfo = soonerDate();
		return eInfo != null ? eInfo.getPlace() : "Null data";
	}


	/**
	 * Get the event likes
	 */
	public long getLikes() {
		return EventManager.getLikes(id);
	}




	//-------------------------

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

	@Override
	public String toString() {
		return "EventContainer [id=" + id + ", title=" + title + ", description=" + description + ", vacancies=" + vacancies
				+ ", image=" + image + ", manager=" + manager + "]";
	}

	/**
	 * Converts an Event into EventContainer
	 */
	public static EventContainer convertTo(Event e) {
		return new EventContainer(e.getId(), e.getTitle(), e.getDescription(), e.getVacancies(), e.getImagePath().get(0), 
				e.getManager().getId());
	}
}
