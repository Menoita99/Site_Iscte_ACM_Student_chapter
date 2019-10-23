package com.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.store.objects.EventContainer;



@ManagedBean
@RequestScoped
public class EventsBean { 
	
	
	private static final int EVENTS_PER_PAGE = 4*3;
	
	private String test = "worked";
	
	
	/**
	 *
	 */
	public Map<Integer,List<EventContainer>> getEventsPage(int page) {
		Map<Integer,List<EventContainer>> map = new HashMap<>();
//		
//		try {
//			List<EventContainer> dbEvents = dataBase.getSortedEvents();
//			
//			List<EventContainer> eventContainers = new ArrayList<EventContainer>();
//			
//			
//			for(int i = page*EVENTS_PER_PAGE ; dbEvents != null && dbEvents.size() > i && i < page*EVENTS_PER_PAGE + EVENTS_PER_PAGE ; i++) {
//				eventContainers.add(dbEvents.get(i));
//				
//				if(i%4==3) {
//					map.put(i/4,eventContainers);
//					eventContainers= new ArrayList<EventContainer>();
//				}
//				
//			}
//				
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
		
		return map;
	}


	public String getTest() {
		return test;
	}



	public void setTest(String test) {
		System.out.println("worked : "+ test);
		this.test = test;
	}
	
}
