package com.database.managers;

import java.util.List;

import com.database.entities.News;

public class NewsManager {

	public static News findByTitle(String title) {
		String escapedTitle = title.replace("'", "''");
		List<News> resutl = JpaUtil.executeQuery("Select n From News n where n.title = ?1", News.class, escapedTitle);
		return resutl.isEmpty() ? null : resutl.get(0);
	}

}
