package com.database.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.database.entities.Event;
import com.database.entities.EventInfo;
import com.database.entities.User;

/**
 *
 *This class manages the interaction with the entity Event
 *
 * @author RuiMenoita
 */
public class EventManager {






	/**
	 * Creates an event
	 */
	public static Event createEvent(int vacancies, String title, String description, String shortDescription, 
			String requirements, List<String> imagePath, List<Date> dates, int managerID, List<String> tags, 
			List<Integer> staffn, List<Date> subscriptionDeadlines, List<String> places){

		Event p = null;
		
		try {
			User manager = UserManager.getUserById(managerID);
			List <User> staff = staffn.stream().map(id -> UserManager.getUserById(id)).collect(Collectors.toList());
			
			p = new Event(vacancies, title, description, shortDescription, requirements, imagePath, manager, tags, staff);
			
			JpaUtil.createEntity(p);
			
			for (int i = 0; i < places.size(); i++) {
				EventInfo ei = new EventInfo(dates.get(i),subscriptionDeadlines.get(i),places.get(i),p);
				p.getInfos().add( ei);
				JpaUtil.createEntity(ei);
			}
			
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






	public static List<Event> findAll() {
		return JpaUtil.executeQuery("Select e from Event e", Event.class);
	}






	public static EventInfo getInfo(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try{
			return em.find(EventInfo.class,id);
		}finally {
			em.close();
		}
	}






}
