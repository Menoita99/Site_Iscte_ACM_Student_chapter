package com.database.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity implementation class for Entity: Material
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Material implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private String name;
}
