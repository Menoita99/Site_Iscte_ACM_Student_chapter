package com.database.entities;

import com.database.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: Investigator
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Investigator implements Serializable {

	private static final long serialVersionUID = 1L;   
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@Exclude
	@lombok.EqualsAndHashCode.Exclude
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_INVESTIGATOR_USER_ID"), nullable= false, name="user_id",unique = true)
	private User user;
	
	private String institution;//ADD MOR INFO TO THIS CLASS
	
	
	
	/**
	 * @param id
	 * @param user
	 * @param institution
	 * @param type
	 */
	public Investigator( User user, String institution) {
		this.user = user;
		this.institution = institution;
	}
   
}
