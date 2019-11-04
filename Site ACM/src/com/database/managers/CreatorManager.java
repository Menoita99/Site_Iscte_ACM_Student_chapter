package com.database.managers;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.database.entities.Event;

public class CreatorManager {

	private static final String OK_CHARS = "abcd fghijkmlnopqr stuvxywzAB CDEFGHIJKLMNOPQR STUVXYWZ0123456789 ";

	private static int nUsers= 1000;
	
	private static int nEvents= 300;
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		for (int i = 0; i < nUsers; i++) {
			String email = generateRandomString(5)+"@"+generateRandomString(3)+"."+generateRandomString(2);
			UserManager.createUser(email, "123456789", generateRandomString(5),generateRandomString(5), generateRandomString(5));
		}
		
		for(int i = 0; i <nEvents;i++) {
			Event e = EventManager.createEvent(new Random().nextInt(100)+1, 
												generateRandomString(10),
												generateRandomString(20), 
												getRandomEventImages(), 
												UserManager.getUserById(new Random().nextInt(nUsers)+1),
												randomTags());
			
			for (int k = 0; k < new Random().nextInt(5); k++) {
				EventManager.addParticipant(e, UserManager.getUserById(new Random().nextInt(nUsers)+1), false);
			}	
			
			
			EventManager.createEventInfo(e,LocalDateTime.of(new Random().nextInt(2)+2019, Month.JANUARY, 1, 10, 10, 30)
										 , LocalDateTime.of(new Random().nextInt(2)+2021, Month.JANUARY, 2, 10, 10, 30)
										 , generateRandomString(30));
			
			for (int j = 0; j < new Random().nextInt(100)+23; j++) {
				EventManager.giveLike(e,UserManager.getUserById(new Random().nextInt(nUsers)+1));
			}
		}
			
	}

	
	
	
	
	/**
	 * returns a list of random images from images that are on events directory
	 */
	private static List<String> getRandomEventImages() {
		List<String> images = new ArrayList<String>();
		
		File dir = new File("WebContent/resources/files/events/");
		int r = new Random().nextInt(5)+1;
		
		for (int i = 0; i < r; i++) {
			int img = new Random().nextInt(dir.list().length);
			if(!images.contains(dir.list()[img]))
				images.add(dir.list()[img]);
		}
		
		return images;
	}



	/**
	 * Generate random tags 
	 */
	private static List<String> randomTags() {
		int bound = 10;
		int nTags = new Random().nextInt(bound);
		List<String> tags = new ArrayList<String>();
		
		for (int i = 0; i < nTags; i++) 
			tags.add(generateRandomString(nTags));
		return tags;
	}

	
	
	/**
	 *generate a random strings
	 *@param size of random string
	 */
	public static String generateRandomString(int length) {
		String output = "";
		if(length>0)
			for (int i = 0; i < length; i++) 
				output+= OK_CHARS.charAt(new Random().nextInt(OK_CHARS.length()));
		return output;
	}
}
