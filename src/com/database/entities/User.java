package com.database.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

/**
 * Entity implementation class for Entity: UserContainer
 *
 * This class represents the table user
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	private int id;
	
	@Column(length = 60 , nullable = false, unique = true)
	private String email;
	
	@Column(length = 60 , nullable = false)
	private String password;
	
	@Column(length = 110)
	private String imagePath = "default/ACM_ICON.png";
	
	@Column(length = 110)
	private String cv;
	
	@Column(length = 25)
	private String course;
	
	@Column(length = 50, nullable = false)
	private String fristName;

	@Column(length = 50, nullable = false)
	private String lastName;
	
	@Column(length = 30)
	private String cellPhone;
	
	@Column(length = 50 , nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_log = new Date(System.currentTimeMillis());
	
	private boolean isAdmin = false;	
	
	private boolean isMember = false;   
	
	private boolean isActive= false;  	

	@Column(nullable = false)
	private int views = 0;  			
	
	@Column(nullable = false,length = 64, unique = true)
	private String activationKey;
	
	@Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy = "participants")
	private Set<Project> projects = new HashSet<>();
	
	
	
	
	/**
	 * @param email
	 * @param password
	 * @param imagePath
	 * @param course
	 * @param fristName
	 * @param lastName
	 * @param cellPhone
	 * @param username
	 * @param isAdmin
	 * @param isMember
	 */
	public User(String email, String password, String imagePath, String course, String fristName, String lastName,
			String cellPhone, String username, boolean isAdmin, boolean isMember,String activationKey) {
		this.email = email;
		this.password = password;
		this.imagePath = "users/"+imagePath;
		this.course = course;
		this.fristName = fristName;
		this.lastName = lastName; 
		this.cellPhone = cellPhone;
		this.username = username;
		this.isAdmin = isAdmin;
		this.isMember = isMember;
		this.activationKey = activationKey;
	}




	/**
	 * @param email
	 * @param password
	 * @param imagePath
	 * @param fristName
	 * @param lastName
	 * @param username
	 */
	public User(String email, String password, String imagePath, String fristName, String lastName, String username
			,String activationKey) {
		this.email = email;
		this.password = password;
		this.imagePath = "users/"+imagePath;
		this.fristName = fristName;
		this.lastName = lastName;
		this.username = username;
		this.activationKey = activationKey;
	}
	
	
	/**
	 * @param email
	 * @param password
	 * @param imagePath
	 * @param fristName
	 * @param lastName
	 * @param username
	 */
	public User(String email, String password, String fristName, String lastName, String username,String activationKey) {
		this.email = email;
		this.password = password;
		this.fristName = fristName;
		this.lastName = lastName;
		this.username = username;
		this.activationKey = activationKey;
	}
	
	
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(activationKey, other.activationKey) && Objects.equals(cellPhone, other.cellPhone)
				&& Objects.equals(course, other.course) && Objects.equals(email, other.email)
				&& Objects.equals(fristName, other.fristName) && Objects.equals(imagePath, other.imagePath)
				&& isActive == other.isActive && isAdmin == other.isAdmin && isMember == other.isMember
				&& Objects.equals(lastName, other.lastName) && Objects.equals(last_log, other.last_log)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username)
				&& views == other.views;
	}




	@Override
	public int hashCode() {
		return Objects.hash(activationKey, cellPhone, course, email, fristName, imagePath, isActive, isAdmin, isMember,
				lastName, last_log, password, username, views);
	}
}