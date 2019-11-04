package com.database.managers;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
	 * This method return a list of events.
	 * 
	 * JPQL query: "SELECT e FROM Event e "+customQueryPart, Event.class
	 */
	public static List<Event> executeCustomQuery(String customQueryPart){
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<Event> query = manager.createQuery( "SELECT e FROM Event e "+customQueryPart, Event.class);								//creates query
		List<Event> results = query.getResultList();																					//get results

		manager.close();
		return results;
	}
	
	
	


	/**
	 * @return all events present on DataBase without discriminate event state 
	 */
	public static List<Event> getAllEvents() {//TODO function that only gives projects that have been approved
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<Event> query = manager.createQuery( "SELECT e FROM Event e", Event.class);								//creates query
		List<Event> results = query.getResultList();																					//get results

		manager.close();
		return results;
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
	public static void addParticipant(Event event, User user, boolean isStaff) {//TODO vacancies
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
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<Event> query = manager.createQuery( "SELECT e FROM Event e WHERE id =" + eventID, Event.class);	//creates query
		Event e = query.getSingleResult();																					//get results

		manager.close();
		return e.getVacancies()>0;
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
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<EventInfo> query = manager.createQuery( "SELECT ei FROM EventInfo ei "
														 + "WHERE event.id ="+id, EventInfo.class);	//creates query
		List<EventInfo> results = query.getResultList();																					//get results

		manager.close();
		return results;
	}




	/**
	 *Return the number of likes of an event
	 *@param id event id 
	 */
	public static long getLikes(int id) {
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<Long> query = manager.createQuery( "SELECT COUNT(*) FROM EventLike el "
				+ "WHERE el.event.id ="+id, Long.class);	//creates query
		long count = query.getSingleResult();														//get results

		manager.close();
		return count;
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
	 * @return true if @param user has already likes @param event
	 */
	public static boolean hasLike(Event event, User user) {
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<EventLike> query = manager.createQuery( "SELECT evtlike FROM EventLike evtlike "
				+ "WHERE evtlike.event.id ="+event.getId()+" and "
				+ " evtlike.user.id = "+user.getId(), EventLike.class);	//creates query
		List<EventLike> result = query.getResultList();														//get results

		manager.close();

		return !result.isEmpty();
	}




	//USED TO TEST FUCNTIONS
	public static void main(String[] args) {
		giveLike(getAllEvents().get(0), UserManager.getUserById(1));
	}
}
