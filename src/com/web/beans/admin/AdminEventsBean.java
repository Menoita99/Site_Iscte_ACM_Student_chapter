package com.web.beans.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.managers.EventManager;
import com.web.containers.EventContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class AdminEventsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<EventContainer> events = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		events = EventManager.findAll().stream().map(EventContainer::new).collect(Collectors.toList());
	}
	
}
