package com.database.managers;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.database.entities.Event;
import com.database.entities.User;

public class CreatorManager {

	private static final String OK_CHARS = "abcdfghijkmlnopqrstuvxywzABCDEFGHIJKLMNOPQRSTUVXYWZ0123456789 ";

	private static final String[] TEXT = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat. On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.".split(" ");

	private static int nUsers= 100;

	private static int nEvents= 30;



	/**
	 * Generates Users and events with complete status
	 */
	public static void main(String[] args) {
		for (int i = 0; i < nUsers; i++) {
			String email = generateRandomPhrases(1)+"@"+generateRandomPhrases(1)+"."+generateRandomPhrases(1);
			User user = UserManager.createUser(email, "123456789", generateRandomPhrases(1),generateRandomPhrases(1), generateRandomPhrases(1));
			if(user == null)
				i--;
		}

		for(int i = 0; i <nEvents;i++) {

			Event e = EventManager.createEvent(new Random().nextInt(100)+1, 
					generateRandomPhrases(2),
					generateRandomPhrases(100), 
					getRandomEventImages(), 
					UserManager.getUserById(new Random().nextInt(nUsers)+1),
					randomTags());

			for (int k = 0; k < new Random().nextInt(e.getVacancies()); k++) {
				try {
					EventManager.addParticipant(e, UserManager.getUserById(new Random().nextInt(nUsers)+1), false);
				}catch(Exception e1) {
					System.out.println("ERROR CREATING PARTICIPANT, I WILL TRY AGAIN");
					k--;
					e1.printStackTrace();
					continue;
				}
			}	


			int year = new Random().nextInt(3)+2018;
			int month = new Random().nextInt(12);
			int day = new Random().nextInt(28)+1;
			int hour = new Random().nextInt(23);

			EventManager.createEventInfo(e,LocalDateTime.of(year, Month.values()[month], day, hour, 10, 30)
					, LocalDateTime.of(year, Month.values()[month], day, hour+1, 10, 30)
					, generateRandomPhrases(7));

			for (int j = 0; j < new Random().nextInt(100)+ new Random().nextInt(100); j++) {
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
			tags.add(generateRandomPhrases(nTags));
		return tags;
	}





	/**
	 *generate random strings
	 *@param size of random string
	 */
	public static String generateRandomString(int length) {
		String output = "";
		if(length>0)
			for (int i = 0; i < length; i++) 
				output+= OK_CHARS.charAt(new Random().nextInt(OK_CHARS.length()));
		return output;
	}



	/**
	 *generate random phrases
	 *@param number of words
	 */
	public static String generateRandomPhrases(int words) {
		String output = "";
		if(words>0)
			for (int i = 0; i < words; i++) 
				output+= TEXT[new Random().nextInt(TEXT.length)]+ (words==1?"":" ");
		return output;
	}
}




