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
	private static final String DB_URL = "jdbc:mysql://localhost/acm_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	//  Database credentials
	private static final String USER = "server";		//Change to use your root username and password
	private static final String PASS = "0001@bombS";

	// Database Connection 
	private Connection dbConnection= null;


	public DataBaseConnection() {
		connectToDataBase();
	}

	/**
	 * Establish the connection between server and database
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
	 * After using ResultSet use the method .close() to avoid connection problems
	 * @throws SQLException
	 */
	public void executeUpdate(String update) throws SQLException {
		Statement stm = dbConnection.createStatement();
		stm.executeUpdate(update);
		stm.close();
	}

	/**
	 * Executes the query given and returns the ResultSet
	 * After using ResultSet use the method .close() to avoid connection problems
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
		return new Timestamp(new Date().getTime()).toString();
	}

	
	/**
	 * This method returns the result Set of the query
	 * Select *  from users where email = ? and password = ?
	 * After using ResultSet use the method .close() to avoid connection problems 
	 */
	public ResultSet loginQuery(String email, String password) throws SQLException {
		PreparedStatement stm= dbConnection.prepareStatement("Select *  from users where email = ? and password = ?");  
		stm.setString(1,email);				//1 specifies the first parameter in the query  
		stm.setString(2,password);			//2 specifies the Second parameter in the query  
		return stm.executeQuery();
	}

	
	//TODO Create your own preparedStatment
	

}
