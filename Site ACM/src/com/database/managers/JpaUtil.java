package com.database.managers;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class JpaUtil {

	private static EntityManagerFactory entityFactory ;
	
	static {
		entityFactory = Persistence.createEntityManagerFactory("acmDB");
	}
	
	
	
	
	
	
	public static EntityManager getEntityManager() {
		return entityFactory.createEntityManager();
	}
	
	
	
	
	
	
	public static void close(){
		entityFactory.close();
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
		EntityManager manager = getEntityManager();									//get's manager instance

		TypedQuery<T> query = manager.createQuery(customQueryPart,resultClass);		//creates query
		List<T> results = query.getResultList();									//get results

		manager.close();
		return results;
	}
	
	
	
	
	
	
	/**
	 * This method executes the query @param cutomQueryPart and returns only one
	 * object @param resultCLass
	 * 
	 * @param customQueryPart query
	 * @param resultClass return class 
	 * 
	 * @return return only one object of type resultClass
	 */
	public static <T> T executeQueryAndGetSingle(String customQueryPart, Class<T> resultClass ){
		EntityManager manager = getEntityManager();									//get's manager instance

		TypedQuery<T> query = manager.createQuery(customQueryPart,resultClass);		//creates query
		T result = query.getSingleResult();											//get results

		manager.close();
		return result;
	}
}
