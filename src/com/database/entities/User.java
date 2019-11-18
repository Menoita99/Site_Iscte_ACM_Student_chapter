package com.database.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: UserContainer
 *
 * This class represents the table user
 */
@Entity
@Table(name = "user")
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
	private String imagePath;
	
	@Column(length = 25)
	private String course;
	
	@Column(length = 50, nullable = false)
	private String fristName;

	@Column(length = 50, nullable = false)
	private String LastName;
	
	@Column(length = 30)
	private String cellPhone;
	
	@Column(length = 50 , nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private LocalDateTime last_log;
	
	private boolean isAdmin = false;	//Default value
	
	private boolean isMember = false;   //Default value

	@Column(nullable = false)
	private int views = 0;  			//Default value
	
	@Column()
	private boolean isActive= false;  	//Default value
	
	@Column(nullable = false,length = 64, unique = true)
	private String activationKey;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @return the course
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @return the fristName
	 */
	public String getFristName() {
		return fristName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return LastName;
	}

	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the last_log
	 */
	public LocalDateTime getLast_log() {
		return last_log;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @return the isMember
	 */
	public boolean isMember() {
		return isMember;
	}

	/**
	 * @return the views
	 */
	public int getViews() {
		return views;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @return the activationKey
	 */
	public String getActivationKey() {
		return activationKey;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(String course) {
		this.course = course;
	}

	/**
	 * @param fristName the fristName to set
	 */
	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	}

	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param last_log the last_log to set
	 */
	public void setLast_log(LocalDateTime last_log) {
		this.last_log = last_log;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @param isMember the isMember to set
	 */
	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	/**
	 * @param views the views to set
	 */
	public void setViews(int views) {
		this.views = views;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @param activationKey the activationKey to set
	 */
	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	@Override
	public int hashCode() {
		return Objects.hash(LastName, activationKey, cellPhone, course, email, fristName, id, imagePath, isActive,
				isAdmin, isMember, last_log, password, username, views);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(LastName, other.LastName) && Objects.equals(activationKey, other.activationKey)
				&& Objects.equals(cellPhone, other.cellPhone) && Objects.equals(course, other.course)
				&& Objects.equals(email, other.email) && Objects.equals(fristName, other.fristName) && id == other.id
				&& Objects.equals(imagePath, other.imagePath) && isActive == other.isActive && isAdmin == other.isAdmin
				&& isMember == other.isMember && Objects.equals(last_log, other.last_log)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username)
				&& views == other.views;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", fristName=" + fristName + ", LastName=" + LastName
				+ ", last_log=" + last_log + ", isAdmin=" + isAdmin + ", isMember=" + isMember + ", isActive="
				+ isActive + "]";
	}
}