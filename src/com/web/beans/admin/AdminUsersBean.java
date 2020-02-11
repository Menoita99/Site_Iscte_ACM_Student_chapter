package com.web.beans.admin;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.database.managers.JpaUtil;
import com.database.managers.UserManager;
import com.web.containers.UserContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class AdminUsersBean implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	private static final int lastMonths = 7;
	
	private List<UserContainer> users;
	
	private Long totalUsers;
	
	private double viewsMonth;
	private double mediaUsersCreatedMonth;
	private Long totalInvestigators;
	private Long totalAcmMembers;
	
	
	@PostConstruct
	public void init() {
		users = UserManager.findAll().stream().map(UserContainer::new).collect(Collectors.toList());
		
		totalUsers = JpaUtil.executeQuery("Select count(*) from User", Long.class).get(0);
		totalAcmMembers = JpaUtil.executeQuery("Select count(*) from User u where u.isMember =  true", Long.class).get(0);
		totalInvestigators = JpaUtil.executeQuery("Select count(*) from Investigator", Long.class).get(0);
		
		mediaUsersCreatedMonth = mediaUsersCreatedMonth();
	}
	

	
	
	
		
	
	/**
	 * 
	 * @return media of users created per month
	 */
	private double mediaUsersCreatedMonth() {
		LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime date = begin.minusYears(now.getYear());
		double total = date.getYear() * 12 + date.getMonthValue() + 1;
		return totalUsers/total;
	}

	
	
	
	
	
	/**
	 * Return the last lastMonths as a string
	 * e.g lastMonths = 6 and today is 26/01/2020
	 * returns
	 * January
	 * December
	 * November
	 * October
	 * September
	 * August
	 */
	public List<String> getLastMonths(){
		LocalDateTime later = LocalDateTime.now().minusMonths(lastMonths-1);
		List<String> months = new ArrayList<>();
		for (int i = 0; i < lastMonths ; i++) {
			months.add((later.getMonth()+"").substring(0, 1)+(later.getMonth()+"").substring(1).toLowerCase());
			later = later.plusMonths(1);
		}
		return months;
	}
	
	
	
	
	
	/**
	 * @return return a list of data that corresponds of how much projects have been created in a specific month
	 */
	public List<Long> getLastMonthsUsersCreated(){
		LocalDateTime later = LocalDateTime.now().minusMonths(lastMonths-1);
		List<Long> months = new ArrayList<>();
		for (int i = 0; i < lastMonths ; i++) {
			months.add(JpaUtil.executeQuery("select count(p) from User p WHERE YEAR(p.creationDate) = "+later.getYear()+" AND MONTH(p.creationDate) = "+later.getMonthValue(), Long.class).get(0));
			later = later.plusMonths(1);
		}
		return months;
	}
	
	
	
	
	
	
	/**
	 * Return all the hours in a day
	 * e.g 0, 1, 2, 3, 4 ... 23
	 */
	public List<Integer> getAllHours(){
		List<Integer> hours = new ArrayList<>();
		for(int i = 0; i < 24; i++) {
			hours.add(i);
		}
		return hours;
	}
	
	
	
	
	
	
	public List<Long> getLoginCounts(){
		List<Long> count = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			count.add(JpaUtil.executeQuery("Select count(*) from User u join u.logs l where HOUR(l) = " + i, Long.class).get(0));
		}
		return count;
	}
	
	
	
	
	
	/**
	 * @return return a list of data that corresponds of how much projects have been created in a specific month
	 */
	public List<Long> getLastMonthsViews(){
		LocalDateTime later = LocalDateTime.now().minusMonths(lastMonths-1);
		List<Long> months = new ArrayList<>();
		for (int i = 0; i < lastMonths ; i++) {
			//months.add(JpaUtil.executeQuery("Select count(*) from User p join p.views v where YEAR(v.date) = "+ later.getYear() +"and MONTH(v.date) = "+later.getMonthValue(), Long.class).get(0));
			later = later.plusMonths(1);
		}
		return months;
	}
	
	
	
	
	
	
	/**
	 * @return return a list of data that corresponds of how much projects have been created in a specific month
	 */
	public List<Long> getLastMonthsLikes(){ //total likes from user to events,projects,researches
		LocalDateTime later = LocalDateTime.now().minusMonths(lastMonths-1);
		List<Long> months = new ArrayList<>();
		for (int i = 0; i < lastMonths ; i++) {
			//months.add(JpaUtil.executeQuery("Select count(*) from User p join p.likes v where YEAR(v.date) = "+ later.getYear() +"and MONTH(v.date) = "+later.getMonthValue(), Long.class).get(0));
			later = later.plusMonths(1);
		}
		return months;
	}
	
	
	
	
	
	
	/**
	 * @param e
	 * removes an users
	 */
	public void removeUser(ActionEvent e ) {
		System.out.println(e.getComponent().getAttributes().get("userId"));
	}
	
	
}
