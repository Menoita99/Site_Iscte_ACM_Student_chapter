package com.database.managers;

import java.util.List;
import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
		List<User> results = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.email = '"+email+"' "+
												  " and u.password = '"+password+"' ", User.class);																					
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
		List<User> user = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.username = '"+username+"' "+
													 " and u.password = '"+password+"' ", User.class);																			
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
		List<User> results = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.activationKey = "
												+ "'"+activationKey+"' ", User.class);																							
		return  results.isEmpty() ? null : results.get(0);																				 	
	}

	


	

	/**
	 * If there isn't an user with the given username it returns null
	 * otherwise returns the user instance
	 */
	public static User getUserByUsername(String username) {
		EntityManager manager = JpaUtil.getEntityManager();					

		TypedQuery<User> query = manager.createQuery( "SELECT u FROM User u WHERE u.username = '"+username+"' ", User.class);		
		List<User> results = query.getResultList();																					

		manager.close();	

		return  results.isEmpty() ? null :results.get(0);																			
	}

	


	

	/**
	 * If there isn't an user with the given email it returns null
	 * otherwise returns the user instance
	 */
	public static User getUserByEmail(String email) {
		List<User> results = JpaUtil.executeQuery("SELECT u FROM User u WHERE u.email = "
												+ "'"+email+"' ", User.class);																					
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
}
