package com.beans;

import javax.faces.bean.RequestScoped;
import jdk.jfr.Name;

@Name("registration")
@RequestScoped
public class Registration {
	
	private String fristName;
	private String lastName;
	private String passWord;
	private String course;
	
	
	public String getFristName() {
		return fristName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPassWord() {
		return passWord;
	}
	public String getCourse() {
		return course;
	}
	public void setFristName(String fristName) {
		this.fristName = fristName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public void setCourse(String course) {
		this.course = course;
	}
}
