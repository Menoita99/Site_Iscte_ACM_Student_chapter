package com.database.managers;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.database.entities.User;
import com.store.objects.UserContainer;

/**
 * This class manages the interaction with the entity User
 * 
 * @author RuiMenoita
 */
public class UserManager {

	private static final int KEY_LENGTH = 64;

	private static String ACCEPTABLE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";

	
	
	
	/**
	 * Creates a UserContainer given:
	 * @param email,@param password, @param fristName, @param lastName, @param username
	 * 
	 * if user already exits it return null
	 */
	public static User createUser(String email, String password,String fristName, String lastName, String username) {
		User user = null;
		if(getUserByEmail(email)==null && getUserByUsername(username)==null) {	//verifies if user already exits

			EntityManager manager = JpaUtil.getEntityManager();

			manager.getTransaction().begin();					//starts the transaction

			user = new User();									//creates an user
			user.setActivationKey(generateActivationKey());
			user.setEmail(email);
			user.setPassword(password);
			user.setFristName(fristName);
			user.setLastName(lastName);
			user.setUsername(username);
			user.setViews(0);
			user.setActive(false);
			user.setAdmin(false);
			user.setMember(false);
			user.setLast_log(LocalDateTime.now().withNano(0));


			manager.persist(user);								//saves user

			manager.getTransaction().commit();					//commits changes to database

			manager.close();
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
		return createUser(uc.getEmail(),password,uc.getFristName(),uc.getLastName(),uc.getUsername());
	}




	/**
	 *if login was successful it will return the user instance.
	 *Otherwise returns null 
	 *Query: "SELECT u FROM User u WHERE u.email = '"+email+"' "+
	         " and u.password = '"+password+"' ", User
	 */
	public static User emailLogin(String email, String password ) {
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<User> query = manager.createQuery( "SELECT u FROM User u WHERE u.email = '"+email+"' "+
				" and u.password = '"+password+"' ", User.class);								//creates query
		List<User> results = query.getResultList();																					//get results

		manager.close();	
		
		if(!results.isEmpty()) {
			updateLastLog(results.get(0));
			return results.get(0);
		}

		return null;	
	}

	



	/**
	 *if login was successful it will return the user instance.
	 *Otherwise returns null 
	 *Query: "SELECT u FROM User u WHERE u.username = '"+username+"' "+
	 		 " and u.password = '"+password+"' ", User.class);
	 */
	public static User usernameLogin(String username, String password ) {
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<User> query = manager.createQuery( "SELECT u FROM User u WHERE u.username = '"+username+"' "+
				" and u.password = '"+password+"' ", User.class);								//creates query
		User user = query.getSingleResult();																			//get results

		manager.close();	
		
		if(user != null) {
			updateLastLog(user);
			return user;
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
	 * JPQL expression : "SELECT u FROM User u WHERE u.activationKey = 'activationKey' " , String
	 */
	public static User getUserByActivationKey(String activationKey) {
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<User> query = manager.createQuery( "SELECT u FROM User u WHERE u.activationKey = '"+activationKey+"' ", User.class);		//creates query
		List<User> results = query.getResultList();																							//get results

		manager.close();	

		return  results.isEmpty() ? null : results.get(0);																				 	//return user
	}

	
	


	/**
	 * If there isn't an user with the given username it returns null
	 * otherwise returns the user instance
	 * JPQL expression : "SELECT u FROM User u WHERE u.username = 'username' " , String
	 */
	public static User getUserByUsername(String username) {
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<User> query = manager.createQuery( "SELECT u FROM User u WHERE u.username = '"+username+"' ", User.class);		//creates query
		List<User> results = query.getResultList();																					//get results

		manager.close();	

		return  results.isEmpty() ? null :results.get(0);																			//return user
	}

	
	


	/**
	 * If there isn't an user with the given email it returns null
	 * otherwise returns the user instance
	 * JPQL expression : "SELECT u FROM User u WHERE u.email = 'email' " , String
	 */
	public static User getUserByEmail(String email) {
		EntityManager manager = JpaUtil.getEntityManager();					//get's manager instance

		TypedQuery<User> query = manager.createQuery( "SELECT u FROM User u WHERE u.email = '"+email+"' ", User.class);	        	//creates query
		List<User> results = query.getResultList();																					//get results

		manager.close();	

		return results.isEmpty() ? null :results.get(0);																			//return user
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
	 * Set's Last_log to LocalDateTime.now()
	 * 
	 * @param user that must be updated
	 */
	private static void updateLastLog(User user) {
		EntityManager manager = JpaUtil.getEntityManager();	
		
		manager.getTransaction().begin();
		
		user.setLast_log(LocalDateTime.now().withNano(0));
		
		manager.merge(user);

		manager.getTransaction().commit();

		manager.close();
	}
	
	
	
	
	//TODO SETTERS
	public static void updateUser(UserContainer userContainer) {
		//TODO
	}

	public static void removeImage(String imagePath,User user) {
		//TODO
	}

	public static void removeAllImage(Collection<String> imagePath,User user) {
		//TODO
	}
	
	public static void addImage(String imagePath,User user) {
		//TODO
	}

	public static void addAllImage(Collection<String> imagePath,User user) {
		//TODO
	}

	public static void setMainImage(String imagePath,User user){
		//TODO
	}

	public static void deleteUser(User user) {
		//TODO
	}

	public static void desactivateUser(User user) {
		//CHANGE KEY
		//TODO
	}

	
	
	
	
	/**
	 * This method generates a random key with ACCEPTABLE_CHARS with
	 * KEY_LENGTH that isn't present in data base
	 * 
	 * Number of possible key =  ACCEPTABLE_CHARS ^ KEY_LENGTH (Math.pow(ACCEPTABLE_CHARS , KEY_LENGTH))
	 */
	private static String generateActivationKey() {
		String key = "";

		do {
			for (int i = 0; i < KEY_LENGTH; i++) 
				key += ACCEPTABLE_CHARS.charAt(new Random().nextInt(ACCEPTABLE_CHARS.length()));
		}while(getUserByActivationKey(key) != null);												//verifies if key already exits

		return key;
	}


	
	

	/**
	 * Converts an UserContainer into an User
	 */
	public static User convertToUser(UserContainer userContainer) {
		if(userContainer.getId() != -1)
			return getUserById(userContainer.getId());
		if(userContainer.getEmail() != null)
			return getUserByEmail(userContainer.getEmail());
		if(userContainer.getUsername() != null)
			return getUserByUsername(userContainer.getUsername());
		return null;
	}



	
	 // Using main to debug and test
	public static void main(String[] args) {
		UserManager.createUser("teste@teste.com", "123456789", "Teste2", "Testão2", "teste");
	}
}
