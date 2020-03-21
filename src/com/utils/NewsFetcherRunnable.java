package com.utils;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.database.entities.News;
import com.database.managers.JpaUtil;

public class NewsFetcherRunnable implements Runnable{

	@Override
	public void run() {

		Document doc = null;
		try {
			doc = Jsoup.connect("https://technews.acm.org/").get();
		} catch (IOException e) {
			// TODO catch exception
			System.out.println("---exception trying to connect or reading html from web---");
			e.printStackTrace();
		}
		String title, authorsInstDate, description;
		int n = 0;
		if(doc != null) {
			
			Elements elems1 = doc.select("div.desktopFontSize:has(a[name]) > span");
			Elements elems = doc.select("div.desktopFontSize:has(a[name])");
			for (Element elem : elems) {
				title = elems1.get(n).text();
				authorsInstDate = elems1.get(n+1).text(); //to be used in the future if necessary
				n = n+3;
				description = elem.text().replace(title, "").replace(authorsInstDate, "").replace("Full Article", "").replace("*May Require Paid Registration", "").trim();
				
				News news = new News(title, description);
				JpaUtil.createEntity(news);


			}

		}

	}

}
