package com.database.managers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import javax.persistence.EntityManager;

import com.database.entities.Event;
import com.database.entities.EventInfo;
import com.database.entities.EventLike;
import com.database.entities.EventParticipant;
import com.database.entities.State;
import com.database.entities.User;

/**
 *
 *This class manages the interaction with the entity Event
 *
 * @author RuiMenoita
 */
public class EventManager {

	private static final String IMAGES_DIR = "events/";
	private static String defaultImage = "default/ACM_ICON.png";


	/**
	 * @param id Event id
	 * @return returns the event if there is an event with the given id otherwise returns null
	 */
	public static Event getUserEventByID(int id) {
		//TODO
		return null;
	}
	
	
	


	/**
	 * @return all events present on DataBase without discriminate event state 
	 */
	public static List<Event> getAllEvents() {//TODO function that only gives projects that have been approved
		return JpaUtil.executeQuery("SELECT e FROM Event e", Event.class);
	}



	/**
	 *Creates an event
	 */
	public static Event createEvent(int vacancies, String title, String description, List<String> imagePath, User user,List<String> tags) {
		Event event = null;

		if(vacancies>0 && title!= null && description!= null && user!= null ) {

			EntityManager manager = JpaUtil.getEntityManager();

			manager.getTransaction().begin();

			event = new Event();
			event.setVacancies(vacancies);
			event.setTitle(title);
			event.setDescription(description);
			event.setManager(user);
			event.setState(State.ON_APPROVAL);

			if(tags!=null) event.getTags().addAll(tags);
			if(imagePath!= null && !imagePath.isEmpty()) 
				for (String img: imagePath) 
					if(img.matches("([^\\s]+(\\.(?i)(jpg|png|gif|jpeg))$)"))		//checks if it is an image
						event.getImagePath().add(IMAGES_DIR+img);
					else
						event.getImagePath().add(defaultImage);

			manager.persist(event);								
			manager.getTransaction().commit();					//commits changes to database
			manager.close();

			addParticipant(event,user,true);
		}
		return event;
	}




	/**
	 * Add's an user to event and specify if user is member of staff or not
	 */
	public static void addParticipant(Event event, User user, boolean isStaff) {
		if(hasVacancies(event.getId())) {
			EntityManager manager = JpaUtil.getEntityManager();
			manager.getTransaction().begin();

			EventParticipant ep = new EventParticipant();
			ep.setEvent(event);
			ep.setUser(user);
			ep.setStaff(isStaff);

			manager.merge(ep);
			manager.getTransaction().commit();			
			manager.close();
			takeVacancy(event);
		}
	}


	/**
	 * Desincrement in one unity the vacancies value of the event with the given id
	 */
	private static void takeVacancy(Event event) {
		if(hasVacancies(event.getId())) {
			EntityManager manager = JpaUtil.getEntityManager();
			manager.getTransaction().begin();
			
			event.setVacancies(event.getVacancies()-1);
			
			manager.merge(event);
			manager.getTransaction().commit();			
			manager.close();
		}
	}




	/**
	 * @return return true if event has at least one vacancy
	 */
	public static boolean hasVacancies(int eventID) {
		Event e = JpaUtil.executeQueryAndGetSingle( "SELECT e FROM Event e WHERE id =" + eventID, Event.class);
		return e != null ? e.getVacancies()>0 : false;
	}




	/**
	 * Creates an event info for an event
	 */
	public static EventInfo createEventInfo(Event event, LocalDateTime startDate,LocalDateTime endDate, String place) {
		EventInfo ei = null;

		if(startDate.isBefore(endDate) && place != null && event != null) {
			EntityManager manager = JpaUtil.getEntityManager();
			manager.getTransaction().begin();

			ei = new EventInfo();

			ei.setEvent(event);
			ei.setStartDate(startDate);
			ei.setEndDate(endDate);
			ei.setPlace(place);

			manager.flush();
			manager.persist(ei);
			manager.getTransaction().commit();
			manager.close();
		}
		return ei;
	}



	/**
	 * This method returns all the EventInfos of a given event id
	 */
	public static List<EventInfo> getEventInfos(int id) {
		return JpaUtil.executeQuery("SELECT ei FROM EventInfo ei "
								  + "WHERE event.id ="+id, EventInfo.class);
	}




	/**
	 *Return the number of likes of an event
	 *@param id event id 
	 */
	public static long getLikes(int id) {
		return JpaUtil.executeQueryAndGetSingle( "SELECT COUNT(*) FROM EventLike el "
												+ "WHERE el.event.id ="+id, Long.class);
	}


	/**
	 * @param user User that gives the like
	 * @param event Event liked 
	 * 
	 * if user already liked this event this will do nothing and will return null
	 */
	public static EventLike giveLike(Event event, User user) {
		EventLike el = null;

		if(event != null && user != null && !hasLike(event,user)) {
			EntityManager manager = JpaUtil.getEntityManager();
			manager.getTransaction().begin();

			el = new EventLike();
			el.setDate(LocalDateTime.now().withNano(0));
			el.setUser(user);
			el.setEvent(event);

			manager.merge(el);
			manager.getTransaction().commit();
			manager.close();
		}

		return el;
	}





	/**
	 * @return true if user has already liked event
	 */
	public static boolean hasLike(Event event, User user) {
		List<EventLike> result = JpaUtil.executeQuery( "SELECT evtlike FROM EventLike evtlike "
													 + "WHERE evtlike.event.id ="+event.getId()+" and "
													 + "evtlike.user.id = "+user.getId(), EventLike.class);														//get results
		return !result.isEmpty();
	}




	//USED TO TEST FUCNTIONS
	public static void main(String[] args) {
		//giveLike(getAllEvents().get(0), UserManager.getUserById(1));
		
//		SELECT eve.event_id, eveinfo.startDate FROM acmdb.event as eve , acmdb.event_info as eveinfo
//		where eve.event_id = eveinfo.event_event_id and eveinfo.startDate >= "2020-01-01 10:10:30";
		LocalDateTime specificDate = LocalDateTime.of(2020, Month.JANUARY, 1, 10, 10, 30);
		
		List<Event> e = JpaUtil.executeQuery("SELECT eve FROM Event eve, EventInfo eveinfo WHERE "
										   + "eve.event_id = eveinfo.id and eveinfo.startDate >= "+specificDate, Event.class);
		for (Event event : e) {
			System.out.println(event.getId());
		}
		
	}
}
