package com.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import com.web.containers.ResearchContainer;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class CreateResearchBean {

	private int phase = 1;

	private ResearchContainer research;
	private Part uploadedFile;
	private List<Part> uploadedFiles = new ArrayList<>();

	private String tag ="";

	private String usernameOrEmail = "";
}
