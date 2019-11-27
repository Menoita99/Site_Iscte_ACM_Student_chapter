package com.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {        
		System.out.println("Created session " + event.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {     
		String lastAccess = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(event.getSession().getLastAccessedTime()));
		System.out.println("Expired session "+event.getSession().getId()+". Last access = "+ lastAccess);
	}

}
