package com.database.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: NewsLetter
 *
 */
@Entity
@Table(name = "newsletter")
public class NewsLetter implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private String email;

	
	
	
	/**
	 * @return the email
	 */
	@Id
	@Column(length = 50, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof NewsLetter))
			return false;
		NewsLetter other = (NewsLetter) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public String toString() {
		return "NewsLetter [email=" + email + "]";
	}
 }
