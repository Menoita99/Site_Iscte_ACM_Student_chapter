package com.beans;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.containers.objects.EventContainer;
import com.database.entities.Event;
import com.database.managers.CreatorManager;
import com.database.managers.EventManager;
import com.database.managers.JpaUtil;



@ManagedBean
@ViewScoped
public class EventsBean implements Serializable{ 

	private static final int PAGINATION_LENGTH = 5;

	private static final long serialVersionUID = 1L;

	private static int ROWS = 3;
	private static int COLUMNS = 3;

	private List<Event> events = EventManager.getAllAprovedEvents();

	private int actualPage = 0;

	private String searchWord = "";
	private String beginDate = "";
	private String endDate = "";

	private String errorMessage;

	private String viewID = CreatorManager.generateRandomString(5);





	/**
	 *returns a list with all the {@link EventContainer} that a specific row
	 *of actualPage have.
	 *returns null if row don't have any {@link EventContainer}
	 */
	public List<EventContainer> getRow(int n){
		if(n<getMaxRow()) {
			List<EventContainer> row = new ArrayList<>();

			int pageStartIndex = actualPage*COLUMNS*ROWS;

			for (int i = n*COLUMNS+pageStartIndex; i < events.size() && i<(n*COLUMNS+COLUMNS + pageStartIndex) ; i++) 
				row.add(new EventContainer(events.get(i)));
			
			return row;
		}
		return null;
	}





	/**
	 * Based on events size give the maximum rows that actualPage can have 
	 */
	public int getMaxRow() {
		if(events.size()<actualPage*ROWS*COLUMNS) return 0;
		return (int)((double)(Math.min(events.size(), (actualPage+1)*ROWS*COLUMNS)-actualPage*ROWS*COLUMNS)/COLUMNS + (double)(COLUMNS-1)/COLUMNS);
	}





	/**
	 *same as getMaxRow() but outputs a array that is used in c:foreach tag at events.xhtml template 
	 */
	public int[] getNumberOfRows() {
		int[] rows = new int[getMaxRow()];
		for (int i = 0; i < rows.length; i++)
			rows[i] = i;
		return rows;
	}





	/**
	 *Calculate the number of pages needed to show all information present on events 
	 */
	public List<Integer> getPages() {
		if(events != null && !events.isEmpty()) {

			List<Integer> pages = new ArrayList<>();

			int maxPages = (int)((double)((events.size())/(ROWS*COLUMNS))+0.5);

			if(actualPage == 0)
				for (int i = 0; i <= maxPages && pages.size()<PAGINATION_LENGTH; i++) 
					pages.add(i);
			else if(actualPage == maxPages)
				for (int i = Math.max(0, maxPages-PAGINATION_LENGTH); i <= maxPages && pages.size()<PAGINATION_LENGTH; i++) 
					pages.add(i);
			else {
				if(actualPage+1 == maxPages) {
					for(int i = Math.max(0, maxPages-PAGINATION_LENGTH); i <=maxPages && pages.size()<PAGINATION_LENGTH;i++)
						pages.add(i);
				}else {
					for(int i = Math.max(0, actualPage-(int)(PAGINATION_LENGTH/2)); i <= actualPage+(int)(PAGINATION_LENGTH/2);i++)
						pages.add(i);
				}
			}
			return pages;
		}

		return null;
	}





	/**
	 *Set's List<Event> events only with the Events that correspond 
	 *with the search data given
	 *
	 *Search word only verifies titles
	 */
	public void search() {
		try {
			setActualPage(0);
			setErrorMessage("");

			List<Event> buffer = new ArrayList<Event>();

			if(!beginDate.isBlank() || !endDate.isBlank()) 								//if at least one date field is set
				buffer = searchByDate(convertToLocalDateTime(beginDate),convertToLocalDateTime(endDate));

			if((beginDate.isBlank() && endDate.isBlank()) && !searchWord.isBlank())		//if no date fields are set but search word is set
				buffer = JpaUtil.executeQuery("SELECT e FROM Event e WHERE UPPER(e.title)"
						+ " LIKE '%"+searchWord.toUpperCase()+"%'", Event.class);

			else if(!buffer.isEmpty() && !searchWord.isBlank()) {						//if one or more date fields are set and search word is set
				List<Event> filtred = new ArrayList<Event>();

				for (Event event : buffer) 
					if(event.getTitle().matches(".*"+searchWord+".*"))
						filtred.add(event);

				buffer=filtred;
			}


			if(beginDate.isBlank() && endDate.isBlank() && searchWord.isBlank())		//all fields are empty
				buffer = EventManager.getAllAprovedEvents();
			
			System.out.println(buffer);
			events = buffer;


		}catch(Exception e) {
			e.printStackTrace();
			setErrorMessage("Something went wrong");
		}

		if(!getErrorMessage().equals(""))	//if it occurs some of error
			events.clear();
	}





	private List<Event> searchByDate(LocalDateTime sDate, LocalDateTime eDate) {
		if(sDate != null & eDate != null) {
			
		}
		return null;
	}





	/**
	 * This method parses a String to a LocalDateTime object
	 * 
	 * @param date String date to be parsed, must have the format "dd/mm/yyyy"
	 * @return returns a LocalDateTime if string could be parsed otherwise returns null
	 */
	private LocalDateTime convertToLocalDateTime(String date) {
		String[] splited = date.split("/");

		LocalDateTime time=null;

		if(splited.length == 3 ) {
			try {
				int year = Math.max(Integer.parseInt(splited[2]),0);
				int month = Integer.parseInt(splited[1]);
				int day = Integer.parseInt(splited[0]);
				time = LocalDateTime.of(year, Month.of(month), day,0,0,0);
			}catch(DateTimeException | NumberFormatException e) {
				return null;
			}
		}
		return time;
	}





	/**
	 * @return true if there is events to display
	 */
	public boolean hasEvents() {
		if(events ==  null) return false;
		return !events.isEmpty() ;
	}





	/**
	 * increments actual page
	 */
	public void incrementActualPage() {
		setActualPage(actualPage+1);
	}





	/**
	 * desincrements actual page
	 */
	public void desincrementActualPage() {
		setActualPage(actualPage-1);
	}





	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}





	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}





	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}





	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}





	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}





	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}





	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}





	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}





	/**
	 * @return the actualPage
	 */
	public int getActualPage() {
		System.out.println("view id : "+viewID +" called : "+actualPage);
		return actualPage;
	}

	
	
	
	
	
	/**
	 * @param actualPage the actualPage to set
	 */
	public void setActualPage(int actualPage) {
		if(actualPage<=(int)((double)((events.size())/(ROWS*COLUMNS))+0.5) && actualPage>=0)
			this.actualPage = actualPage;
	}

	//To debug proposes
	public List<Event> getEvents(){
		return events;
	}



	//Used to debug and test
	public static void main(String[] args) {
		EventsBean eb = new EventsBean();
		eb.setSearchWord("l");
		eb.search();
		eb.setActualPage(8);
		System.out.println(eb.hasEvents());
		System.out.println(Arrays.toString(eb.getNumberOfRows()));
		for (int i :eb.getNumberOfRows()) {
			for(EventContainer e :eb.getRow(i)) {
				if(e != null) {
					System.out.println(e.getTitle());
					System.out.println(e.getLikes());
				}
			}
		}
		System.out.println();
	}
}
