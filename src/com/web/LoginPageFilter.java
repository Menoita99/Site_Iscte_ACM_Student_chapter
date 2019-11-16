package com.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginPageFilter implements Filter{


	/**
	 * This filter will redirect an user to login page
	 * It will be active when an user tries to go into a page that is required to be logged in
	 * 
	 * This filter also saves the last page that user enter before it redirects do login page
	 * to when user performs with success the login , to be redirect to the page that he was.
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {


		HttpSession session = ((HttpServletRequest) req).getSession(true);		//If there is no session it creates one to store "currentPage" and "lastPage" attributes
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String loginURI = request.getContextPath() + "/acm/templates/login.xhtml";	//login url

		if(session.getAttribute("user_id") == null) {

			String newCurrentPage = request.getServletPath();

			if (session.getAttribute("currentPage") == null) {
				session.setAttribute("lastPage", newCurrentPage); 						
				session.setAttribute("currentPage", newCurrentPage); 					
			}else { 

				String oldCurrentPage = session.getAttribute("currentPage").toString(); 

				if (!oldCurrentPage.equals(newCurrentPage)) { 								
					session.setAttribute("lastPage", oldCurrentPage); 
					session.setAttribute("currentPage", newCurrentPage); 
				} 
			}

			response.sendRedirect(loginURI);

		}else
			chain.doFilter(request, response);
	}





}