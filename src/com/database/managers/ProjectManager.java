package com.database.managers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.database.entities.AcmLike;
import com.database.entities.Project;
import com.database.entities.ProjectCandidate;
import com.database.entities.User;
import com.database.entities.View;
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
			e.printStackTrace();
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
		Project p = findById(projectId);
		User u = UserManager.getUserById(userId);
		
		for(AcmLike l : p.getLikes())
			if(l.getUser().equals(u))
				return null;
		
		AcmLike like = new AcmLike(u);
		
		p.getLikes().add(like);
		JpaUtil.mergeEntity(p);
		
		return like;
	}






	/**
	 * Removes the like that user gave.
	 * If user didn't like the project, won't do nothing
	 */
	public static void dislike(int projectId, int userId) {
		Project p = findById(projectId);
		
		for(AcmLike l : p.getLikes()) {
			if(l.getUser().getId() == userId) {
				p.getLikes().remove(l);
				JpaUtil.mergeEntity(p);
				JpaUtil.deleteEntity(l);
				return;
			}
		}
	}





	/**
	 * Given the user ID it returns all the projects that user liked, 
	 * if he didn't liked projects it returns an empty list
	 */
	public static List<Project> getLikedProjects(int userId) {
		return JpaUtil.executeQuery("Select p from Project p join p.likes l where l.user.id = "+userId,Project.class);
	}





	/**
	 * 
	 */
	public static Project getLikedProject(int userId, int projectId) {
		List<Project> rst = JpaUtil.executeQuery("Select p from Project p join p.likes l where l.user.id = "+userId+" and p.id = "+projectId,Project.class);
		return rst.isEmpty() ? null : rst.get(0);
	}





	/**
	 * 
	 * @param project
	 */	
	public static void updateProject(ProjectContainer project) {
		// TODO Auto-generated method stub

	}





	/**
	 *Gives a view to given project
	 *
	 * @param id project id
	 */
	public static void addView(int id) {
		View v = new View();
		JpaUtil.createEntity(v);

		Project project = findById(id);
		project.getViews().add(v);
		JpaUtil.mergeEntity(project);
	}

	
	
	
	
	/**
	 * @return return if  user has liked the given project
	 */
	public static boolean wasLiked(int projectId, int userId) {
		Project p = ProjectManager.findById(projectId);
		for(AcmLike l : p.getLikes())
			if(l.getUser().getId() == userId)
				return true;
		return false;
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
