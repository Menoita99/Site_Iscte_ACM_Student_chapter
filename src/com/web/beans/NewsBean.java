package com.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.database.entities.News;
import com.database.managers.JpaUtil;

import lombok.Data;

@ManagedBean
@ViewScoped
@Data
public class NewsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public List<News> news;
	
	
	public NewsBean() {
		news = JpaUtil.executeQuery("Select n from News n", News.class);
	}

}
