package com.database.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Console {
	
	
	public static void main(String[] args) throws Exception {
 		EntityManagerFactory emf = Persistence.createEntityManagerFactory("acmDB");
		EntityManager em = emf.createEntityManager();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		for (;;) {
			
			System.out.print("JPQL> ");
			String query = reader.readLine();
			
			if (query.equals("quit")) 
				break;
			
			if (query.isBlank()) 
				continue;
			
			try {
				
				List<?> result = em.createQuery(query).getResultList();
				if (!result.isEmpty()) {
					int count = 0;
					for (Object o : result) {
						
						if(o  instanceof Object[]) {
							for(Object ob : (Object[]) o) 
								System.out.println(count++ + " ->	" + ob);
						}else
							System.out.println(count++ + " ->	" + o);
					}
					
				} else {
					System.out.println("0 results returned");
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.out.println("\n");
			}
		}
	} 
}
