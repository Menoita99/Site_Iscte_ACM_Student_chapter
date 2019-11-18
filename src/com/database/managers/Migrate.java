package com.database.managers;

import javax.persistence.Persistence;

public class Migrate {

	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("acmDB");
	}
}
