package com.utils.comparators;

import java.util.Comparator;

import com.web.containers.ProjectContainer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectAlphabeticComparator implements Comparator<ProjectContainer>{

	private boolean isAscending = true;
	
    public ProjectAlphabeticComparator(boolean asc) {
		isAscending = asc;
	}
	
	@Override
	public int compare(ProjectContainer o1, ProjectContainer o2) {
		do {
			//TODO
		}while(false);
		return 0;
	}

}
