package com.database.managers;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;


@ApplicationScoped
public class JpaUtil {

	private static EntityManagerFactory entityFactory ;

	static {
		entityFactory = Persistence.createEntityManagerFactory("acmDB");
	}





	/**
	 * @return returns an instance of EntityManager created my EntityManagerFactory
	 */
	public static EntityManager getEntityManager() {
		return entityFactory.createEntityManager();
	}






	public static void close(){
		entityFactory.close();
	}
	
	
	
	
	
	
	/**
	 * This method executes the query @param cutomQueryPart and returns a List<?>
	 * 
	 * @param customQueryPart query

	 * @return return a list of objects 
	 */
	public static List<?> executeUntypedQuery(String customQueryPart){
		EntityManager manager = getEntityManager();									//get's manager instance
		
		try {
			return manager.createQuery(customQueryPart).getResultList();			//creates query
		}finally {
			manager.close();
		}
	}





	
	/**
	 * This method executes the query @param cutomQueryPart and returns a List
	 * of object @param resultCLass
	 * 
	 * @param customQueryPart query
	 * @param resultClass return class 
	 * 
	 * @return return a list of objects of type resultClass
	 */
	public static <T> List<T> executeQuery(String customQueryPart, Class<T> resultClass ){
		EntityManager manager = getEntityManager();										//get's manager instance
		try {
			TypedQuery<T> query = manager.createQuery(customQueryPart,resultClass);		//creates query
			List<T> results = query.getResultList();									//get results

			return results;
		}finally {
			manager.close();
		}
	}






	/**
	 * This method executes the query cutomQueryPart and returns only one
	 * object resultCLass
	 * 
	 * If there is
	 * 
	 * @param customQueryPart query
	 * @param resultClass return class 
	 * 
	 * @return return only one object of type resultClass
	 * 
	 * @throws NoResultException if there is no result
     * @throws NonUniqueResultException if more than one result
     * @throws IllegalStateException if called for a Java
     *         Persistence query language UPDATE or DELETE statement
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode other than
     *         <code>NONE</code> has been set and there is no transaction
     *         or the persistence context has not been joined to the
     *         transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds 
     *         the query timeout value set and the transaction 
     *         is rolled back 
	 */
	public static <T> T executeQueryAndGetSingle(String customQueryPart, Class<T> resultClass ){
		EntityManager manager = getEntityManager();										//get's manager instance
		try {
			TypedQuery<T> query = manager.createQuery(customQueryPart,resultClass);		//creates query
			T result = query.getSingleResult();											//get results

			return result;
		}finally {
			manager.close();
		}
	}

	




	/**
	 * Takes an entity instance and inserts it in data base
	 * @param entity entity to be inserted
	 */
	public static void createEntity(Object entity) {
		EntityManager manager = getEntityManager();	
		try {
			manager.getTransaction().begin();					
			manager.persist(entity);
			manager.flush();
			manager.getTransaction().commit();			
		}finally {
			manager.close();
		}
	}
	
	
	
	
	
	
	/**
	 * Deletes the given entity from database
	 * @param entity entity
	 */
	public void deleteEntity(Object entity) {
		EntityManager manager = getEntityManager();	
		try {
			manager.getTransaction().begin();					
			manager.remove(entity);	
			manager.getTransaction().commit();			
		}finally {
			manager.close();
		}
	}






	
	/**
	 * 
	 * @param ep
	 */
	public static void mergeEntity(Object entity) {
		EntityManager manager = JpaUtil.getEntityManager();	
		try {
			manager.getTransaction().begin();					
			manager.merge(entity);
			manager.flush();
			manager.getTransaction().commit();			
		}finally {
			manager.close();
		}
	}
}
