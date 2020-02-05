package com.database.managers;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.database.entities.AcmLike;
import com.database.entities.Candidate;
import com.database.entities.Investigator;
import com.database.entities.Research;
import com.database.entities.ResearchType;
import com.database.entities.State;
import com.database.entities.User;
import com.database.entities.View;
import com.web.containers.CandidateContainer;

public class ResearchManager {
	
	
	/**
	 * @return returns null if it could not create the investigator, otherwise returns the investigator created
	 */
	public static Investigator createInvestigator(int userId, String institution) {
		Investigator inv= null;
		try {
			User u = UserManager.getUserById(userId);
			inv = new Investigator(u, institution);
			
			JpaUtil.createEntity(inv);
		} catch (Exception e) {
			inv = null;
			e.printStackTrace();
		}
		return inv;
	}
	
	
	
	
	
	/**
	 * 
	 * @param title 
	 * @param description 
	 * @param shortDescription 
	 * @param requirements 
	 * @param tags 
	 * @param imagePath 
	 * @param type 
	 * @param institutions 
	 * @param usefullLinks 
	 * @param investigatorId 
	 * @return
	 */
	public static Research createResearch( List<Integer> investigatorsId, String title, String description, String shortDescription, String requirements,
										 List<String> tags, List<String> imagePath, ResearchType type, List<String> institutions, List<String> usefullLinks) {
		Research re = null;
		try {
			re = new Research(institutions,usefullLinks,investigatorsId.stream().map(id -> findInvestigator(id)).collect(Collectors.toList())
							, title, description, shortDescription, requirements, tags, imagePath, type);
			JpaUtil.createEntity(re);
		} catch (Exception e) {
			re = null;
			e.printStackTrace();
		}
		return re;
	}

	
	
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Investigator findInvestigator(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try{
			return em.find(Investigator.class,id);
		}finally {
			em.close();
		}
	}
	
	
	
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Research findResearch(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try{
			return em.find(Research.class,id);
		}finally {
			em.close();
		}
	}




	/**
	 * @return return all researches
	 */
	public static List<Research> findAllResearches() {
		return JpaUtil.executeQuery("Select r from Research r ", Research.class);
	}





	/**
	 * @param id
	 */
	public static void addView(int id) {
		View v = new View();
		Research r = findResearch(id);
		r.getViews().add(v);
		JpaUtil.mergeEntity(r);
	}




	/**
	 * 
	 * @param userId
	 * @param researchId
	 * @return
	 */
	public static Candidate getCandidature(int userId, int researchId) {
		List<Candidate> cand =  JpaUtil.executeQuery("Select c from Research r join r.candidates c where r.id = "
				 +researchId+" and c.user.id = "+userId, Candidate.class);
		return cand.isEmpty() ? null : cand.get(0);
	}




	/**
	 * @return return if  user has liked the given research
	 */
	public static boolean wasLiked(int researchId, int userId) {
		return !JpaUtil.executeQuery("Select r from Research r join r.likes l where l.user.id = "+ userId +" and r.id= "+researchId, Research.class).isEmpty();
	}
	
	
	
	
	
	/**
	 * Creates an object Acmlike with research id and user id given
	 * if object to be created already exists it returns false;
	 */
	public static AcmLike like(int researchId, int userId) {
		Research r = findResearch(researchId);
		User u = UserManager.getUserById(userId);

		if(wasLiked(researchId, userId))
			return null;

		AcmLike like = new AcmLike(u);

		r.getLikes().add(like);
		JpaUtil.mergeEntity(r);

		return like;
	}






	/**
	 * Removes the like that user gave.
	 * If user didn't like the research, won't do nothing
	 */
	public static void dislike(int researchId, int userId) {
		Research p = findResearch(researchId);

		List<AcmLike> result = JpaUtil.executeQuery("Select l from Research r join r.likes l where l.user.id = "+userId+" and r.id= "+researchId, AcmLike.class);
		if(!result.isEmpty()) {
			AcmLike l = result.get(0);
			p.getLikes().remove(l);
			JpaUtil.mergeEntity(p);
			JpaUtil.deleteEntity(l);
		}
	}




	/**
	 * Creates a candidate from given research
	 * @param researchId research id
	 */
	public static Candidate createCandidate(int researchId, CandidateContainer candidature) {
		Candidate c = null;
		try {
			Research r = ResearchManager.findResearch(researchId);
			c = new Candidate(candidature);
			r.getCandidates().add(c);
			JpaUtil.mergeEntity(r);
		}catch(Exception e ) {
			e.printStackTrace();
			c = null;
		}
		return c ;
	}





	/**
	 * @return return all accepted events
	 */
	public static List<Research> findAllAccepted() {
		String query = "Select r from Research r Where ";
		
		List<State> acceptanceStates = State.getAcceptanceStates();
		for (int i = 0; i < acceptanceStates.size(); i++) {
			query += " r.state = "+acceptanceStates.get(i).ordinal();
			if(i != acceptanceStates.size()-1)
				query+=" or ";
		}
		return JpaUtil.executeQuery(query, Research.class);
	}








	public static Investigator findInvestigatorByUserId(int id) {
		return JpaUtil.executeQuery("Select i from Investigator i where i.user.id = "+id, Investigator.class).get(0);
	}
	
}
