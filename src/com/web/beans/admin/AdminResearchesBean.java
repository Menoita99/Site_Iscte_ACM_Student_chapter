package com.web.beans.admin;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.managers.ResearchManager;
import com.web.containers.ResearchContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class AdminResearchesBean {

	private List<ResearchContainer> researches;
	
	@PostConstruct
	public void init() {
		researches = ResearchManager.findAllResearches().stream().map(ResearchContainer::new).collect(Collectors.toList());
	}
}
