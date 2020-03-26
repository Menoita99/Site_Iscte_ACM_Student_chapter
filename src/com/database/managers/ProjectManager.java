package com.database.managers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.database.entities.AcmLike;
import com.database.entities.Candidate;
import com.database.entities.Project;
import com.database.entities.State;
import com.database.entities.User;
import com.database.entities.View;
import com.web.containers.CandidateContainer;
import com.web.containers.ProjectContainer;

public class ProjectManager {





	/**
	 * @return return all projects from data base
	 */
	public static List<Project> findAll(){
		return JpaUtil.executeQuery("Select p from Project p", Project.class);
	}
	
	
	
	
	
	/**
	 * @return return all accepted events
	 */
	public static List<Project> findAllAccepted() {
		String query = "Select p from Project p Where ";
		
		List<State> acceptanceStates = State.getAcceptanceStates();
		for (int i = 0; i < acceptanceStates.size(); i++) {
			query += " p.state = "+acceptanceStates.get(i).ordinal();
			if(i != acceptanceStates.size()-1)
				query+=" or ";
		}
		return JpaUtil.executeQuery(query, Project.class);
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

		if(wasLiked(projectId, userId))
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

		List<AcmLike> result = JpaUtil.executeQuery("Select l from Project p join p.likes l where l.user.id = "+userId+" and p.id= "+projectId, AcmLike.class);
		if(!result.isEmpty()) {
			AcmLike l = result.get(0);
			p.getLikes().remove(l);
			JpaUtil.mergeEntity(p);
			JpaUtil.deleteEntity(l);
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
		Project project = findById(id);
		project.getViews().add(v);
		JpaUtil.mergeEntity(project);
	}





	/**
	 * @return return if  user has liked the given project
	 */
	public static boolean wasLiked(int projectId, int userId) {
		return !JpaUtil.executeQuery("Select p from Project p join p.likes l where l.user.id = "+ userId +" and p.id= "+projectId, Project.class).isEmpty();
	}







	//----Project Candidate Manager ----//





	/**
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	public static Candidate getCandidature(int userId , int projectId) {
		List<Candidate> cand =  JpaUtil.executeQuery("Select c from Project p join p.candidates c where p.id = "
													 +projectId+" and c.user.id = "+userId, Candidate.class);
		return cand.isEmpty() ? null : cand.get(0);
	}


	/**
	 * @return return all candidatures of a project
	 */
	public static List<Candidate> getCandidates(int projectId){
		return JpaUtil.executeQuery("Select c from  Project p join p.candidates c where p.id = "+projectId, Candidate.class);
	}


<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD


	/**
	 * 
	 * Adds a user and merges the database
	 * 
	 * @param userId
	 * @param projectId
	 */
	public static void addParticipant(int userId, int projectId) {
		System.out.println("ADD");
		Project p = findById(projectId);
		User u = UserManager.getUserById(userId);
		p.getParticipants().add(u);
		JpaUtil.mergeEntity(p);
	}

	/**
	 * 
	 * Removes a user and merges the database
	 * 
	 * @param userId
	 * @param projectId
	 */
	public static void removeParticipant(int userId, int projectId) {
		Project p = findById(projectId);
		User u = UserManager.getUserById(userId);
		p.getParticipants().remove(u);
		JpaUtil.mergeEntity(p);
	}

=======
	/**
	 * Creates a candidate for given project 
	 * @param userId user that wants to candidate
	 * @param porjectId project to add the candidate
	 * @return returns the candidate or null if candidate already exits
	 */
	public static Candidate createCandidate(int projectId, CandidateContainer candidature) {
		Candidate c = null;
		try {
			Project p = findById(projectId);
			c = new Candidate(candidature);
			p.getCandidates().add(c);
			JpaUtil.mergeEntity(p);
		} catch (Exception e) {
			e.printStackTrace();
			c = null;
		}
		return c;
	}





	public static void removeMember(int id, int projectId) {
		// TODO Auto-generated method stub
		
	}
>>>>>>> branch 'master' of https://github.com/Menoita99/Site_Iscte_ACM_Student_chapter.git
=======
>>>>>>> parent of a41a048... Update commit
=======
>>>>>>> parent of a41a048... Update commit
}
