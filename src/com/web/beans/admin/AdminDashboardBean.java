package com.web.beans.admin;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.database.entities.Event;
import com.database.entities.Project;
import com.database.entities.Research;
import com.database.managers.EventManager;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.database.managers.ResearchManager;
import com.web.containers.EventContainer;
import com.web.containers.ProjectContainer;
import com.web.containers.ResearchContainer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import lombok.Data;

@ManagedBean
@ApplicationScoped
@Data
public class AdminDashboardBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int numberOfActivities = 3;
			
	
	private String instagramPost1;
	private String instagramPost2;
	
	private List<EventContainer> events = new ArrayList<>();
	private List<ProjectContainer> projects = new ArrayList<>();
	private List<ResearchContainer> researches = new ArrayList<>();
	
	private double totalViews;
	
	private double montlyViews;
	
	
	@PostConstruct
	public void init() {
		instagramPost1 = "https://www.instagram.com/p/B3NbDlohLQj/";
		instagramPost2 = "https://www.instagram.com/p/B5tHB46h3UW/";
		
		List<Event> e = EventManager.findAllAccepted();
		List<Project> p = ProjectManager.findAllAccepted();
		List<Research> r = ResearchManager.findAllAccepted();
		
		for (int i = 0; i < numberOfActivities; i++) {
			if(!e.isEmpty() && i<e.size()) events.add(new EventContainer(e.get(i)));
			if(!p.isEmpty() && i<p.size()) projects.add(new ProjectContainer(p.get(i)));
			if(!r.isEmpty() && i<r.size()) researches.add(new ResearchContainer(r.get(i)));
		}
		
		totalViews = totalViewsCalc();
		montlyViews = mediaMontlyViews();
		
	}
	
	
	/**
	 * 
	 * Calculates the total of views
	 * 
	 * @return long totalViews
	 */
	private long totalViewsCalc() {
		long pTotalViews = JpaUtil.executeQuery("Select count(*) from Project p join  p.views", Long.class).get(0);
		long rTotalViews = JpaUtil.executeQuery("Select count(*) from Research r join  r.views", Long.class).get(0);
		long eTotalViews = JpaUtil.executeQuery("Select count(*) from Event e join e.views", Long.class).get(0);
		
		return pTotalViews + rTotalViews + eTotalViews;
	}
	
	
	
	private double mediaMontlyViews() {
		LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime date = begin.minusYears(now.getYear());
		double total = date.getYear() * 12 + date.getMonthValue() + 1;
		return totalViews/total;
	}
	
	
	
	

	
}
