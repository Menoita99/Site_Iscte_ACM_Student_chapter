package com.database.managers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.database.entities.AcmLike;
import com.database.entities.Event;
import com.database.entities.EventInfo;
import com.database.entities.User;
import com.database.entities.View;

/**
 *
 *This class manages the interaction with the entity Event
 *
 * @author RuiMenoita
 */
public class EventManager {






	/**
	 * Creates an event
	 * @param endDates 
	 */
	public static Event createEvent(int vacancies, String title, String description, String shortDescription, 
			String requirements, List<String> imagePath, List<Date> dates, int managerID, List<String> tags, 
			List<Integer> staffn, List<Date> subscriptionDeadlines, List<String> places, List<Date> endDates){

		Event p = null;
		
		try {
			User manager = UserManager.getUserById(managerID);
			List <User> staff = staffn.stream().map(id -> UserManager.getUserById(id)).collect(Collectors.toList());
			
			p = new Event(vacancies, title, description, shortDescription, requirements, imagePath, manager, tags);
			
			for (int i = 0; i < places.size(); i++) 
				p.getInfos().add(new EventInfo(dates.get(i),endDates.get(i),subscriptionDeadlines.get(i),places.get(i),p,staff));
			
			JpaUtil.createEntity(p);
			
		}catch (Exception e) {
			p=null;
			e.printStackTrace();
		}
		
		return p;
	}






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
	 * This method returns the event with the given title
	 * This method is NOT case sensitive
	 * 
	 * @param title event title
	 */
	public static Event getEventByTitle(String title) {
		List<Event> result = JpaUtil.executeQuery("Select e from Event e where lower(e.title) = lower('"+ title +"')", Event.class);
		return result.isEmpty() ? null : result.get(0);
	}





	/**
	 * @return return all events
	 */
	public static List<Event> findAll() {
		return JpaUtil.executeQuery("Select e from Event e", Event.class);
	}





	/**
	 *
	 */
	public static EventInfo getInfo(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try{
			return em.find(EventInfo.class,id);
		}finally {
			em.close();
		}
	}





	/**
	 *Gives a view to given event
	 *
	 * @param id project id
	 */
	public static void addView(int id) {
		View v = new View();
		Event event = getEventById(id);
		event.getViews().add(v);
		JpaUtil.mergeEntity(event);
	}





	/**
	 * @return return if  user has liked the given event
	 */
	public static boolean wasLiked(int eventId, int userId) {
		return !JpaUtil.executeQuery("Select e from Event e join e.likes l where l.user.id = "+ userId +" and e.id= "+ eventId, Event.class).isEmpty();
	}





	/**
	 * 
	 * @param eventId
	 * @param userId
	 */
	public static void dislike(int eventId, int userId) {
		Event e = getEventById(eventId);

		List<AcmLike> result = JpaUtil.executeQuery("Select l from Event e join e.likes l where l.user.id = "+userId+" and e.id= "+eventId, AcmLike.class);
		if(!result.isEmpty()) {
			AcmLike l = result.get(0);
			e.getLikes().remove(l);
			JpaUtil.mergeEntity(e);
			JpaUtil.deleteEntity(l);
		}
	}




	/**
	 * Creates an object Acmlike with event id and user id given
	 * if object to be created already exists it returns false;
	 * @return 
	 */
	public static AcmLike like(int eventId, int userId) {
		Event e = getEventById(eventId);	
		User u = UserManager.getUserById(userId);
		
		if(wasLiked(eventId, userId))
			return null;
		
		AcmLike like = new AcmLike(u);
		
		e.getLikes().add(like);
		JpaUtil.mergeEntity(e);
		
		return like;
	}



	public static void main(String[] args) {
		System.out.println(JpaUtil.executeQuery("Select l from Event e join e.likes l where l.user.id = 301 and e.id= 15", AcmLike.class));
	}





	/**
	 * 
	 * @param id
	 * @return
	 */
	public static List<User> getStaff(int id) {
		// TODO Auto-generated method stub
		return null;
	}



}
