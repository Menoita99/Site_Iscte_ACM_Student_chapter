package com.database.managers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.database.entities.Project;
import com.database.entities.User;

public class ProjectManager {

	/**
	 * @return return all projects from data base
	 */
	public static List<Project> findAll(){
		return JpaUtil.executeQuery("Select p from Project p", Project.class);
	}
	

	
	/**
	 * Creates a project
	 * if project was created with success it returns the project otherwise it returns null
	 */
	public static Project createproject(int maxMembers, String title, String description, String requirements, Date deadLine, Date subscriptionDeadline, 
										int managerID, List<String> tags, List<String> imagePath) {
		Project p = null;
		
		try {
			User manager = UserManager.getUserById(managerID);
			p = new Project(maxMembers, title, description,requirements, deadLine, subscriptionDeadline, manager, tags, imagePath);
			JpaUtil.createEntity(p);
		}catch (Exception e) {
			p = null;
			System.out.println("-------------------ERROR CREATING PROJECT-------------------");
			System.out.println();
			System.out.println(e.getMessage());
			System.out.println();
			e.printStackTrace();
			System.out.println("------------------------------------------------------------");
		}
		
		return p ;
	}



	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Project findById(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try{
			return em.find(Project.class,id);
		}finally {
			em.close();
		}
	}
	
	
	
	
}
