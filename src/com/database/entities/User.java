package com.database.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.web.containers.UserContainer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * Entity implementation class for Entity: UserContainer
 *
 * This class represents the table user
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column( nullable = false, unique = true)
	private int id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date(System.currentTimeMillis());
	
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
	
	
	@Exclude
	@ElementCollection(targetClass=Date.class)
	@Temporal(TemporalType.TIMESTAMP)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Date> logs = new ArrayList<>();
	
	private boolean isAdmin = false;	
	
	private boolean isMember = false;   
	
	private boolean isActive= false;  	
	
	@Exclude
	@Column(length = 665)	
	private String about;
	
	@Column(nullable = false,length = 64, unique = true)
	private String activationKey;
	
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "user_views",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "view_id"))
	private List<View> views = new ArrayList<>();
	
	
	
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
			String cellPhone, String username, boolean isAdmin, boolean isMember,String activationKey,String about) {
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
		this.about = about;
		this.logs.add( new Date(System.currentTimeMillis()));
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
			,String activationKey,String about) {
		this.email = email;
		this.password = password;
		this.imagePath = "users/"+imagePath;
		this.fristName = fristName;
		this.lastName = lastName;
		this.username = username;
		this.activationKey = activationKey;
		this.about = about;
		this.logs.add( new Date(System.currentTimeMillis()));
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
		this.logs.add( new Date(System.currentTimeMillis()));
	}
	
	
	
	
	
	public void update(UserContainer update) {
		if(update.getEmail() != null && !update.getEmail().isEmpty())
			email = update.getEmail();
		if(update.getFirstName() != null && !update.getFirstName().isEmpty())
			fristName = update.getFirstName();
		if(update.getLastName() != null && !update.getLastName().isEmpty())
			lastName = update.getLastName();
		if(update.getCellPhone() != null && !update.getCellPhone().isEmpty())
			cellPhone = update.getCellPhone();
		if(update.getCourse() != null && !update.getCourse().isEmpty())
			course = update.getCourse();
		if(update.getUsername() != null && !update.getUsername().isEmpty())
			username = update.getUsername();
		if(update.getAbout() != null && !update.getAbout().isEmpty())
			about = update.getAbout();
		if(update.getImagePath() != null && !update.getImagePath().isEmpty())
			imagePath = update.getImagePath();
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activationKey == null) ? 0 : activationKey.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fristName == null) ? 0 : fristName.hashCode());
		result = prime * result + id;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (activationKey == null) {
			if (other.activationKey != null)
				return false;
		} else if (!activationKey.equals(other.activationKey))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fristName == null) {
			if (other.fristName != null)
				return false;
		} else if (!fristName.equals(other.fristName))
			return false;
		if (id != other.id)
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}





	
	
	
	




}