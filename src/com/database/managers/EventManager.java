package com.database.managers;

import java.time.LocalDateTime;
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
	 * @param id event id
	 * @return returns the event with the given id
	 */
	public static Event getEventById(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try{
			return em.find(Event.class,id);
		}finally {
			em.close();
		}
	}





	/**
	 * @return all events present on DataBase without discriminate event state 
	 */
	public static List<Event> getAllAprovedEvents() {
		return JpaUtil.executeQuery("Select e from Event e where e.state = 'APPROVED'", Event.class); 
	}





	/**
	 *Creates an event
	 */
	public static Event createEvent(int vacancies, String title, String description, List<String> imagePath, User user,List<String> tags) {
		Event event = null;

		if(vacancies>0 && title!= null && description!= null && user!= null ) {

			event = new Event();
			event.setVacancies(vacancies);
			event.setTitle(title);
			event.setDescription(description);
			event.setManager(user);
			event.setState(State.APPROVED);

			if(tags!=null) event.addAllTags(tags);
			if(imagePath!= null && !imagePath.isEmpty()) 
				for (String img: imagePath) 
					if(img.matches("([^\\s]+(\\.(?i)(jpg|png|gif|jpeg))$)"))		//checks if it is an image
						event.addImage(IMAGES_DIR+img);
					else
						event.addImage(defaultImage);

			JpaUtil.createEntity(event);

			addParticipant(event,user,true);
		}
		return event;
	}






	/**
	 * Add's an user to event and specify if user is member of staff or not
	 */
	public static EventParticipant addParticipant(Event event, User user, boolean isStaff) {
		List<EventParticipant> l = JpaUtil.executeQuery("SELECT part FROM EventParticipant part WHERE "
				+ " part.event.id = "+event.getId()+" and part.user.id = "+user.getId(), 
				EventParticipant.class);

		if(hasVacancies(event.getId()) && l.isEmpty()) {
			EventParticipant ep = new EventParticipant();
			ep.setEvent(event);
			ep.setUser(user);
			ep.setStaff(isStaff);

			JpaUtil.mergeEntity(ep);

			return ep;
		}

		return null;
	}






	/**
	 * Add's an user to event and specify if user is member of staff or not
	 */
	public static EventParticipant addParticipant(int eventID, int userID, boolean isStaff) {
		List<EventParticipant> l = JpaUtil.executeQuery("SELECT part FROM EventParticipant part WHERE "
				+ " part.event.id = "+eventID+" and part.user.id = "+userID, 
				EventParticipant.class);

		Event event = getEventById(eventID);
		User user = UserManager.getUserById(userID);

		if(hasVacancies(eventID) && l.isEmpty()) {
			EventParticipant ep = new EventParticipant();
			ep.setEvent(event);
			ep.setUser(user);
			ep.setStaff(isStaff);

			JpaUtil.mergeEntity(ep);

			return ep;
		}

		return null;
	}






	/**
	 * Removes Event participant from data base.
	 * if it is the manager or can't find Event Manager it will return null;

	 * @param eventId event id
	 * @param userId user id
	 */
	public static EventParticipant removeParticipant(int eventId, int userId) {
		EventParticipant ep = null;
		List<EventParticipant> results = JpaUtil.executeQuery("Select ep from EventParticipant ep where ep.user.id = "+userId+" and ep.event.id = "+eventId, EventParticipant.class);
		
		if(!results.isEmpty()) {
		
			ep = results.get(0);
			User user = UserManager.getUserById(userId);
			User manager = getEventManager(eventId);
			
			if(user != null && manager != null && !user.equals(manager)) 
				JpaUtil.deleteEntity(ep);
		}
		
		return ep;
	}






	
	/**
	 * @param eventId event id
	 * @return return the manager of the given id
	 */
	private static User getEventManager(int eventId) {
		List<User> results = JpaUtil.executeQuery("Select e.manager from Event e where e.id = "+eventId, User.class);
		return results.isEmpty() ? null : results.get(0) ;
	}

	
	




	/**
	 * @return return true if event has at least one vacancy
	 */
	public static boolean hasVacancies(int eventID) {
		List<Event> e = JpaUtil.executeQuery( "SELECT e FROM Event e WHERE id =" + eventID, Event.class);

		return e.isEmpty() ? false : e.get(0).getVacancies() > getOccupation(eventID);
	}





	/**
	 * 
	 * @param eventID Event id
	 * @return returns the number of participants registered in the event
	 */
	public static long getOccupation(int eventID) {
		return JpaUtil.executeQuery(" select count(*) from EventParticipant p where p.event.id = "
				+ eventID+" and p.isStaff = false", Long.class).get(0);
	}






	/**
	 * Creates an event info for an event
	 */
	public static EventInfo createEventInfo(Event event, LocalDateTime startDate,LocalDateTime endDate, String place) {
		EventInfo ei = null;

		if(startDate.isBefore(endDate) && place != null && event != null) {
			ei = new EventInfo();

			ei.setEvent(event);
			ei.setStartDate(startDate);
			ei.setEndDate(endDate);
			ei.setPlace(place);

			EntityManager manager = JpaUtil.getEntityManager();	
			try {
				manager.getTransaction().begin();	
				manager.merge(ei);
				manager.flush();
				manager.getTransaction().commit();			
			}finally {
				manager.close();
			}
		}
		return ei;
	}







	/**
	 * This method returns all the EventInfos of a given event id
	 * @param id Event id
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






	/**
	 * 
	 * @param eventID id of the event to be checked
	 * @param userID id of the user user to be checked
	 * 
	 * @return return true if user is already a participant of this event
	 * 		
	 */
	public static boolean isParticipant(int eventID, int userID) {
		List<EventParticipant> result = JpaUtil.executeQuery( "Select p FROM EventParticipant p Where p.event.id = "+eventID+" and p.user.id = "+ userID
				,EventParticipant.class);														
		return !result.isEmpty();
	}






	/**
	 * @param id Event id
	 * @return return all the staff members of the given id
	 */
	public static List<User> getStaffParticipants(int id) {
		return JpaUtil.executeQuery("Select p.user from EventParticipant p Where p.event.id = "+id+" and p.isStaff = true", User.class);
	}




	/**
	 * 
	 * @param userId user id
	 * @return returns a list with all the events that a given user is in
	 */
	public static List<Event> getParticipations(int userId) {
		return JpaUtil.executeQuery("SELECT DISTINCT e FROM Event e, EventParticipant ep "
				+ "WHERE e.id = ep.event.id AND ep.user.id ="+ userId, Event.class);
	}

}
