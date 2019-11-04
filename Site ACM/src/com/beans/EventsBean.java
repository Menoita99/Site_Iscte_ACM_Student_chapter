package com.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.entities.Event;
import com.database.managers.CreatorManager;
import com.database.managers.EventManager;
import com.store.objects.EventContainer;



@ManagedBean
@ViewScoped
public class EventsBean implements Serializable{ 

	private static final long serialVersionUID = 1L;

	private static int ROWS = 3;
	private static int COLUMNS = 3;

	private List<Event> events = EventManager.getAllEvents();

	private int actualPage = 0;

	private String searchWord;
	private LocalDateTime beginDate;
	private LocalDateTime endDate;

	private String errorMessage;

	private String idTeste = CreatorManager.generateRandomString(5);

	
	
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
				row.add(EventContainer.convertTo(events.get(i)));

			return row;
		}
		return null;
	}


	


	/**
	 * Based on events size give the maximum rows that actualPage can have 
	 */
	public int getMaxRow() {
		if(events.size()<actualPage*ROWS*COLUMNS) return 0;
		return (int)((double)(Math.min(events.size(), (actualPage+1)*ROWS*COLUMNS)-actualPage*ROWS*COLUMNS)/COLUMNS + 0.5);
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
				for (int i = 0; i <= maxPages && pages.size()<5; i++) 
					pages.add(i);
			else if(actualPage == maxPages)
				for (int i = Math.max(0, maxPages-5); i <= maxPages && pages.size()<5; i++) 
					pages.add(i);
			else {
				if(actualPage+1 == maxPages) {
					for(int i = Math.max(0, maxPages-5); i <=maxPages && pages.size()<5;i++)
						pages.add(i);
				}else {
					for(int i = Math.max(0, actualPage-2); i <=actualPage+2;i++)
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
	 */
	public void search() {
		if(beginDate.isBefore(endDate)) {		//checks if the end date is before begin date
			events = EventManager.getEventsWithPatternAndDates(searchWord, beginDate, endDate);
			if(events==null) 
				setErrorMessage("Somethig went wrong");
			else
				setErrorMessage(null);
		}else {
			events.clear();
			setErrorMessage("The begin date must be before the end date");
		}
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
	 * @return the actualPage
	 */
	public int getActualPage() {
		System.out.println(idTeste);
		System.out.println(actualPage);
		return actualPage;
	}

	/**
	 * @param actualPage the actualPage to set
	 */
	public void setActualPage(int actualPage) {
		if(actualPage<=(int)((double)((events.size())/(ROWS*COLUMNS))+0.5) && actualPage>=0)
			this.actualPage = actualPage;
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
	
	
	

	public static void main(String[] args) {
		EventsBean eb = new EventsBean();
		for (int j = 0; j < eb.getPages().size(); j++) {
			System.out.println("-------------PAGE "+j+"-------------------");
			eb.setActualPage(j);
			for (int i = 0; i < eb.getMaxRow(); i++) {
				for (EventContainer ec : eb.getRow(i)) {
					System.out.print(ec.getTitle()+" | ");
				}
				System.out.println("");
			}
		}
	}
}
