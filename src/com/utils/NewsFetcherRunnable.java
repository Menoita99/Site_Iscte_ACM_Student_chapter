package com.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.database.entities.News;
import com.database.managers.JpaUtil;
import com.database.managers.NewsManager;

public class NewsFetcherRunnable implements Runnable{

	@Override
	public void run() {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://technews.acm.org/").get();

			Elements elems = doc.select("div.desktopFontSize:has(a[name])");
			if(doc != null) {
				for (int i = 0; i < elems.size(); i++) {
					String img = elems.get(i).select("img[src]").isEmpty() ? "" : elems.get(i).select("img[src]").get(0).absUrl("src");
					String title = elems.get(i).select("span > b").text();
					String font = elems.get(i).select("span > i > b").text();
					String[] splited = elems.get(i).select("span > i > span").html().split("<br>");
					String author = splited.length == 2 ? "" : splited[1];
					String date = splited.length == 2 ? splited[1] : splited[2];
					String url = elems.get(i).select("span").get(3).select("a").get(0).absUrl("href");
					String description = elems.get(i).ownText();
					String[] dateSplited = date.replace(",", "").split(" ");
					Date dt = java.sql.Date.valueOf(LocalDate.of(Integer.parseInt(dateSplited[2]), Month.valueOf(dateSplited[0].toUpperCase()), Integer.parseInt(dateSplited[1])));

					News news = new News(title, description, font, author, url, dt, img);
					if(NewsManager.findByTitle(news.getTitle()) == null)
						JpaUtil.createEntity(news);
				}
			}
		} catch (IOException e) {
			System.out.println("---exception trying to connect or reading html from web---");
			e.printStackTrace();
		}
	}



	public static void main(String[] args) {
		new NewsFetcherRunnable().run();
	}
}
