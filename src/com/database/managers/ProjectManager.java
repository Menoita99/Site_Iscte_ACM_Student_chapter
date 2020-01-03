package com.database.managers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.database.entities.AcmLike;
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


	/**
	 * Creates an object Acmlike with project id and user id given
	 * if object to be created already exists it returns false;
	 */
	public static AcmLike like(int projectId, int userId) {
		if(wasLiked(userId,projectId) 
				|| findById(projectId) == null 
				|| UserManager.getUserById(userId)==null) 	
			return null;

		AcmLike like = new AcmLike(UserManager.getUserById(userId), findById(projectId));
		try {
			JpaUtil.createEntity(like);
		}catch (Exception e) {
			like = null;
			e.printStackTrace();
		}
		return like;
	}



	/**
	 * @return true if given user already have liked given project
	 */
	public static boolean wasLiked(int userId, int projectId) {
		return ! JpaUtil.executeQuery("Select l from AcmLike l where l.user.id = "+userId +" and l.project.id = "+projectId, AcmLike.class).isEmpty();
	}





	/**
	 * Removes a given AcmLike
	 * @param acmLike like to me removed
	 */
	public static void dislike(AcmLike acmLike) {
		JpaUtil.deleteEntity(acmLike);
	}



//	public static void main(String[] args) {
//		dislike(JpaUtil.executeQueryAndGetSingle("Select l from AcmLike l where l.user.id = 1", AcmLike.class));
//	}
}
