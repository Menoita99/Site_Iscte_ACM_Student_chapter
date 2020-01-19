package com.utils.comparators;

import java.util.Comparator;

import com.web.containers.ProjectContainer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *Time comparator to sort Projects by time 
 * 
 * @author RuiMenoita
 */
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTimeComparator implements Comparator<ProjectContainer> {

	private boolean compareByDeadLine = true;
	private boolean isAscending = true;
	
	@Override
	public int compare(ProjectContainer o1, ProjectContainer o2) {
		int result = 0;
		if(compareByDeadLine) 
			result = (int) (o1.getDeadline().toInstant().getEpochSecond() - o2.getDeadline().toInstant().getEpochSecond());
		else
			result = (int) (o1.getSubscriptionDeadline().toInstant().getEpochSecond() - o2.getSubscriptionDeadline().toInstant().getEpochSecond());
	
		return isAscending  || result == 0 ? result : - result;
	}

}
