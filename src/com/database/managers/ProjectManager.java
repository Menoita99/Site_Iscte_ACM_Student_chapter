package com.database.managers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.database.entities.AcmLike;
import com.database.entities.Project;
import com.database.entities.ProjectCandidate;
import com.database.entities.User;
import com.web.containers.ProjectContainer;

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
	public static Project createproject(int maxMembers, String title, String description, String shortDescription, String requirements, Date deadLine, Date subscriptionDeadline, 
			int managerID, List<String> tags, List<String> imagePath) {
		Project p = null;

		try {
			User manager = UserManager.getUserById(managerID);
			p = new Project(maxMembers, title, description, shortDescription,requirements, deadLine, subscriptionDeadline, manager, tags, imagePath);
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
	
	
	
	
	
	/**
	 * Given the user ID it returns all the projects that user liked, 
	 * if he didn't liked projects it returns an empty list
	 */
	public static List<Project> getLikedProjects(int userId) {
		return JpaUtil.executeQuery("Select l.project from AcmLike l where l.user.id = "+userId,Project.class);
	}
	
	
	
	
	
	/**
	 *@return return an AcmLike object that correspond to the like that the given user  gave to the given project
	 */
	public static AcmLike getLikedProject(int userId, int projectId) {
		List<AcmLike> rst = JpaUtil.executeQuery("Select l from AcmLike l where l.user.id = "+userId+" and l.project.id = "+projectId,AcmLike.class);
		return rst.isEmpty() ? null : rst.get(0);
	}
	
	
	
	
	
	/**
	 * 
	 * @param project
	 */
	public static void updateProject(ProjectContainer project) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	//----Project Candidate Manager ----//
	
	
	
	
	
	/**
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	public static ProjectCandidate getCandidature(int userId , int projectId) {
		List<ProjectCandidate> cand = JpaUtil.executeQuery("Select c from ProjectCandidate c where c.user.id = "+userId
														  +" and c.project.id = "+projectId, ProjectCandidate.class);
		
		return cand.isEmpty() ? null : cand.get(0);
	}


	/**
	 * @return return all candidatures of a project
	 */
	public static List<ProjectCandidate> getCandidates(int projectId){
		return JpaUtil.executeQuery("Select c from ProjectCandidate c where c.project.id = "+projectId, ProjectCandidate.class);
	}


}
