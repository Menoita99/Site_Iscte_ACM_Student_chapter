package com.database.managers;

import java.util.List;
import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;

import com.database.entities.Candidate;
import com.database.entities.Event;
import com.database.entities.Investigator;
import com.database.entities.Project;
import com.database.entities.Research;
import com.database.entities.User;
import com.database.entities.View;
import com.web.containers.UserContainer;

/**
 * This class manages the interaction with the entity User
 * 
 * @author RuiMenoita
 */
public class UserManager {

	private static final int KEY_LENGTH = 64;

	private static String ACCEPTABLE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
	
	
	
	/**
	 * @return returns all users
	 */
	public static List<User> findAll() {
		return JpaUtil.executeQuery("Select u from User u", User.class);
	}


	

	/**
	 * Creates an user, if user already exits it returns null
	 */
	public static User createUser(String email, String password,String fristName, String lastName, String username) {
		User user = null;
		if(getUserByEmail(email)==null && getUserByUsername(username)==null) {	//verifies if user already exits
			
			try {
				user = new User(email,password,fristName,lastName,username,generateActivationKey());									
				JpaUtil.createEntity(user);
			}catch (Exception e) {
				user = null;
				e.printStackTrace();
			}
		}
		return user;
	}

	


	

	/**
	 * Creates an user, if user already exits it returns null
	 */
	public static User createUser(String email, String password, String imagePath, String fristName, String lastName, String username,String about) {
		User user = null;
		if(getUserByEmail(email)==null && getUserByUsername(username)==null) {	//verifies if user already exits
			
			try {
				user = new User(email,password,imagePath,fristName,lastName,username,generateActivationKey(),about);									
				JpaUtil.createEntity(user);
			}catch (Exception e) {
				user = null;
				e.printStackTrace();
			}
		}
		
		return user;
	}
	


	

	/**
	 * Creates an user given:
	 * @param uc
	 * 
	 * if user already exits it return null
	 */
	public static User createUser(UserContainer uc,String password) {
		return createUser(uc.getEmail(),password,uc.getFirstName(),uc.getLastName(),uc.getUsername());
	}

	


	

	/**
	 *if login was successful it will return the user instance.
	 *Otherwise returns null 
	 */
	public static User emailLogin(String email, String password ) {
		List<User> results = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.email = ?1 "+
												  " and u.password = ?2 ", User.class, email, password);																					
		if(!results.isEmpty()) {
			updateLastLog(results.get(0));
			return results.get(0);
		}
		return null;	
	}

	


	

	/**
	 *if login was successful it will return the user instance.
	 *Otherwise returns null 
	 */
	public static User usernameLogin(String username, String password ) {
		List<User> user = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.username = ?1 "+
													 " and u.password = ?2 ", User.class, username, password);																			
		if(!user.isEmpty()) {
			updateLastLog(user.get(0));
			return user.get(0);
		}

		return  null;	
	}

	


	

	/**
	 * If there isn't an user with the given id it returns null
	 * otherwise returns the user instance
	 */
	public static User getUserById(int id) {
		EntityManager manager = JpaUtil.getEntityManager();	
		User user = manager.find(User.class, id);
		manager.close();		
		return user;
	}

	


	

	/**
	 * If there isn't an user with the given activationKey it returns null
	 * otherwise returns the user instance
	 */
	public static User getUserByActivationKey(String activationKey) {
		List<User> results = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.activationKey = ?1 ", 
				User.class, activationKey);																							
		return  results.isEmpty() ? null : results.get(0);																				 	
	}

	


	

	/**
	 * If there isn't an user with the given username it returns null
	 * otherwise returns the user instance
	 */
	public static User getUserByUsername(String username) {
		List<User> results = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.username = ?1 ", User.class, username);

		return  results.isEmpty() ? null :results.get(0);																			
	}

	


	

	/**
	 * If there isn't an user with the given email it returns null
	 * otherwise returns the user instance
	 */
	public static User getUserByEmail(String email) {
		List<User> results = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.email = ?1", User.class, email);																					
		return results.isEmpty() ? null : results.get(0);																			
	}

	


	

	/**
	 * This method activates an user account 
	 * if user account is active or became active returns true.
	 * if it can't find the key in database it returns false
	 */
	public static boolean activateUser(String activationKey) {
		EntityManager manager = JpaUtil.getEntityManager();		
		
		User u = getUserByActivationKey(activationKey);

		if(u == null) return false;
		if(u.isActive()) return true;

		manager.getTransaction().begin();

		u.setActive(true);

		manager.merge(u);

		manager.getTransaction().commit();

		manager.close();
		return getUserByActivationKey(activationKey).isActive();
	}

	


	

	/** 
	 * updates last log
	 * @param user that must be updated
	 */
	private static void updateLastLog(User user) {
		user.getLogs().add(new Date(System.currentTimeMillis()));
		JpaUtil.mergeEntity(user);
	}


	
	
	
	
	/**
	 * This method generates a random key with ACCEPTABLE_CHARS with
	 * KEY_LENGTH that isn't present in data base
	 * 
	 * Number of possible key =  ACCEPTABLE_CHARS ^ KEY_LENGTH (Math.pow(ACCEPTABLE_CHARS , KEY_LENGTH))
	 */
	private static String generateActivationKey(){
		String key = "";
		do {
			for (int i = 0; i < KEY_LENGTH; i++) 
				key += ACCEPTABLE_CHARS.charAt(new Random().nextInt(ACCEPTABLE_CHARS.length()));
		}while(getUserByActivationKey(key) != null);												//verifies if key already exits

		return key;
	}



	
	
	/**
	 * Adds a view to the given user
	 */
	public static void addView(int id) {
		View v = new View();
		User u = getUserById(id);
		u.getViews().add(v);
		JpaUtil.mergeEntity(u);
	}




	/**
	 * @return return all the projects the user joined
	 */
	public static List<Project> getJoinedProject(int id) {
		return JpaUtil.executeQuery(" Select p from Project p join p.participants u where u.id = ?1", Project.class, String.valueOf(id));
	}




	/**
	 * @return return all the events that user is staff of
	 */
	public static List<Event> getJoinedEvents(int id) {
		return JpaUtil.executeQuery("Select distinct e from Event e join e.infos i join i.participants p where p.id = ?1", Event.class, String.valueOf(id));
	}




	/**
	 * @return return all the Researches that user is participant
	 */
	public static List<Research> getJoinedResearches(int id) {
		return JpaUtil.executeQuery("Select r from Research r join r.participants u where u.id =?1 ", Research.class, String.valueOf(id));
	}



	/**
	 * @param id
	 * @return
	 */
	public static List<Event> getLikedEvents(int id) {
		return JpaUtil.executeQuery("Select e from Event e join e.likes l where l.user.id = ?1", Event.class, String.valueOf(id));
	}



	/**
	 * 
	 * @param id
	 * @return
	 */
	public static List<Research> getLikedResearches(int id) {
		return JpaUtil.executeQuery("Select r from Research r join r.likes l where l.user.id = ?1", Research.class, String.valueOf(id));
	}


	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static List<Candidate> getProjectsCandidatures(int id) {
		return JpaUtil.executeQuery("Select c from Project p join p.candidates c where c.user.id = ?1", Candidate.class, String.valueOf(id));
	}




	public static List<Candidate> getResearchesCandidatures(int id) {
		return JpaUtil.executeQuery("Select c from Research p join p.candidates c where c.user.id = ?1", Candidate.class, String.valueOf(id));
	}



	/**
	 * Updates user given an user container
	 * @param id user to be updated
	 * @param user info to update
	 */
	public static void updateUser(int id, UserContainer user) {
		User u = getUserById(id);
		u.update(user);
		JpaUtil.mergeEntity(u);
	}



	/**
	 * Return if the user is an investigator
	 * @param id
	 * @return
	 */
	public static boolean isInvestigator(int id) {
		return !JpaUtil.executeQuery("Select i From Investigator i where i.user.id = ?1", Investigator.class, String.valueOf(id)).isEmpty();
	}
}
