package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

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
	
	@Column(nullable = false)
	private int views = 0;

	@Column( nullable = false)	
	private String title;
	
	@Column( nullable = false, length = 665)	
	private String description;
	
	@Column(nullable = false)
	private String url;

	@Column()
	private String author;
	
	@Column(nullable = false)
	private String site;
	
	@Column(nullable = false)
	private LocalDateTime date;

	@Column()
	@ElementCollection(targetClass=String.class)
	private List<String> tags;
	
	
	
	public News(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	
}
