package com.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

/**
 * Entity implementation class for Entity: News
 *
 */
@Entity
@Data
@NoArgsConstructor
public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "news_id")
	private int id;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "news_views", joinColumns = @JoinColumn(name = "news_id"), inverseJoinColumns = @JoinColumn(name = "view_id"))
	private List<View> views = new ArrayList<>();

	@Column( nullable = false, unique = true)	
	private String title;
	
	@Column( nullable = false, length = 1000)	
	private String description;
	
	@Column(nullable = false)
	private String font;

	@Column
	private String author;
	
	@Column(nullable = false)
	private String url;
	
	@Column(nullable = false)
	private Date date;
	
	@Column
	private String img;
	
	
	/**
	 * @param title
	 * @param description
	 * @param font
	 * @param author
	 * @param url
	 * @param date
	 * @param img
	 */
	public News(String title, String description, String font, String author, String url, Date date, String img) {
		this.title = title;
		this.description = description;
		this.font = font;
		this.author = author;
		this.url = url;
		this.date = date;
		this.img = img;
	}

}
