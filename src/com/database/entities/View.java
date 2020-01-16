package com.database.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

/**
 * Entity implementation class for Entity: View
 *
 */
@Entity
@Data
public class View implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	private int id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
}
