package com.web.beans.admin;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;

import com.database.entities.Event;
import com.database.entities.Project;
import com.database.entities.Research;
import com.database.managers.EventManager;
import com.database.managers.JpaUtil;
import com.database.managers.ProjectManager;
import com.database.managers.ResearchManager;
import com.utils.NewsFetcherRunnable;
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
	
	//hours:minutes:seconds time when daily news fetch is called
	private int HOURS = 1;
	private int MINUTES = 42;
	private int SECONDS = 0;
	
	
	private int numberOfActivities = 3;
			
	
	private String instagramPost1;
	private String instagramPost2;
	
	private List<EventContainer> events = new ArrayList<>();
	private List<ProjectContainer> projects = new ArrayList<>();
	private List<ResearchContainer> researches = new ArrayList<>();
	
	
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
		
		startDailyNewsFetch();
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
	
	
	
	public double averageMontlyViews() {
		LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime date = begin.minusYears(now.getYear());
		double total = date.getYear() * 12 + date.getMonthValue() + 1;
		return totalViewsCalc()/total;
	}
	
	
	
	
	
	
	
	public List<String> getAllMonths(){
		return List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
	}
	
	
	
	
	
	
	
	//Researches
	/**
	 * @return return a list of data that corresponds of how much researches have been created in a specific month
	 */
	public List<Long> getLastMonthsResearchesViews(){
		List<Long> views = new ArrayList<>();
		for (int i = 1; i <= 12 ; i++) {
			views.add(JpaUtil.executeQuery("Select count(*) from Research r join r.views v where MONTH(v.date) = " + i, Long.class).get(0));
		}
		return views;
	}
	
	
	
	
	
	
	
	//Events
		/**
		 * @return return a list of data that corresponds of how much researches have been created in a specific month
		 */
		public List<Long> getLastMonthsEventsViews(){
			List<Long> views = new ArrayList<>();
			for (int i = 1; i <= 12 ; i++) {
				views.add(JpaUtil.executeQuery("Select count(*) from Event e join e.views v where MONTH(v.date) = " + i, Long.class).get(0));
			}
			return views;
		}
		
		
		
		
		
		public double getMonthEBudget() {
			LocalDateTime now = LocalDateTime.now();
			
			return JpaUtil.executeQuery("Select sum(m.quantity * m.price) from Event e join e.material m where YEAR(e.deadLine) = " + now.getYear() + " and MONTH(e.deadLine) = " + now.getMonthValue(), Long.class).get(0);			
		}
		
		
		
		
		//Projects
		
		/**
		 * @return return a list of data that corresponds of how much researches have been created in a specific month
		 */
		public List<Long> getLastMonthsProjectsViews(){
			List<Long> views = new ArrayList<>();
			for (int i = 1; i <= 12 ; i++) {
				views.add(JpaUtil.executeQuery("Select count(*) from Project p join p.views v where MONTH(v.date) = " + i, Long.class).get(0));
			}
			return views;
		}
		
		
		
		public List<Double> getMonthPBudget() {
			LocalDateTime now = LocalDateTime.now();
			List<Double> list = new ArrayList<Double>();
			List<Double> budget = JpaUtil.executeQuery("Select sum(m.quantity * m.price) from Project p join p.material m where YEAR(p.creationDate) = " 
					+ now.getYear() + " and MONTH(p.creationDate) = " + now.getMonthValue(), Double.class);
			list.add(budget.get(0) == null  ? 0 : budget.get(0));
			list.add(500.0);  //Calcular budget do event
			return list;
		}
		
		
		
		/**
		 * Created a tread pool that periodically will fetch news
		 */
		public void startDailyNewsFetch() {
			ZonedDateTime now = ZonedDateTime.now(ZoneId.of("GMT"));
			ZonedDateTime nextRun = now.withHour(HOURS).withMinute(MINUTES).withSecond(SECONDS);
			
			System.out.println("Started daily news fetcher, next run : "+nextRun);
			
			if(now.compareTo(nextRun) > 0)
			    nextRun = nextRun.plusDays(1);

			Duration duration = Duration.between(now, nextRun);
//			long initalDelay = duration.getSeconds();
			long initalDelay = 0;

			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);            
			scheduler.scheduleAtFixedRate(new NewsFetcherRunnable(), initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
		}
	

	
}
