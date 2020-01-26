package com.database.managers;

import java.util.List;

import javax.persistence.EntityManager;

import com.database.entities.Investigator;
import com.database.entities.Research;
import com.database.entities.ResearchType;
import com.database.entities.User;

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
	 * @param investigatorId 
	 * @return
	 */
	public static Research createResearch( int investigatorId, String title, String description, String shortDescription, String requirements,
										 List<String> tags, List<String> imagePath, ResearchType type) {
		Research re = null;
		try {
			re = new Research(findInvestigator(investigatorId), title, description, shortDescription, requirements, tags, imagePath, type);
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
}
