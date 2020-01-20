package com.database.entities;

import com.database.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


/**
 * Entity implementation class for Entity: AcmLike
 *
 */
@Entity
@Data
@NoArgsConstructor
public class AcmLike implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date(System.currentTimeMillis());
	
	@lombok.EqualsAndHashCode.Exclude
	@Exclude
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_LIKE_ID"), nullable= false)
	private User user;
	
	
	public AcmLike(User u) {
		this.user = u;
	}
}
