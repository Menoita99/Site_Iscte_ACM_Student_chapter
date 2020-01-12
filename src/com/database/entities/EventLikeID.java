package com.database.entities;

import java.io.Serializable;


import javax.persistence.*;

import lombok.Data;

/**
 * Entity implementation class for Entity: EventLikeID
 *
 */
@Embeddable
@Data
public class EventLikeID implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private int userID;
	
	@Column(nullable = false)
	private int eventID;
}
