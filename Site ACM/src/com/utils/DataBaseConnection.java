package com.utils;

import java.sql.*;
import java.util.Date;


/**
	 * @author Rui Menoita
	 *
	 *Class that establish the connection to DataBase using JDBC
	 *This class provide methods to consult and updating Data base
	 */

public class DataBaseConnection {

	private static  DataBaseConnection INSTANCE = null;

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/bomermanb2_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	//  Database credentials
	private static final String USER = "server";
	private static final String PASS = "0001@bombS";

	// Database Connection -> criar uma classe que aprefeiçoa a conneção
	private Connection dbConnection= null;


	public DataBaseConnection() {
		connectToDataBase();
	}

	/**
	 * Establish the connection between server a database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void connectToDataBase(){
		try {
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("FAIL LOADING DRIVERS");
			e.printStackTrace();
		}
		try {
			// Open a connection
			dbConnection = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			System.out.println("FAIL CONNECTIONG TO DATABASE");
			e.printStackTrace();
		}	
	}



	public static DataBaseConnection getInstance() {
		INSTANCE = INSTANCE == null ? new DataBaseConnection() : INSTANCE;
		return INSTANCE;
	}


	/**
	 * Executes the @param update query  
	 * @throws SQLException
	 */
	public void executeUpdate(String update) throws SQLException {
		Statement stm = dbConnection.createStatement();
		stm.executeUpdate(update);
	}

	/**
	 * Executes the query given and returns the ResultSet
	 * 
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String query) throws SQLException {
		Statement stm = dbConnection.createStatement();
		return stm.executeQuery(query);
	}


	/**
	 * Returns the date in a format that is accepted by SQL.
	 *
	 * Suggestion: Use TIMESTAMP on query instead
	 */
	public String getSqlTime() {
		Timestamp date =  new Timestamp(new Date().getTime());
		return date.toString();
	}

	
	//TODO Create your own preparedStatment
	
	


	public static void main(String[] args) {
		DataBaseConnection.getInstance();
		System.out.println("DATABASE CONNECTION ESTABLISHED");
	}

}
