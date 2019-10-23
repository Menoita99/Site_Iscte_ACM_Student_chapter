package com.database.entities;

import javax.persistence.Persistence;

public class Migrate {

	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("acmDB");
	}
}
