package com.database.managers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
}
