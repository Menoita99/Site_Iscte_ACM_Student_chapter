package com.utils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;


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
			String[] data = loadDefinitions();
			dbConnection = DriverManager.getConnection(data[0],data[1],data[2]);
		} catch (SQLException e) {
			System.out.println("FAIL CONNECTIONG TO DATABASE");
			e.printStackTrace();
		}	
	}

	/**
	 * This method load the file definitions.txt that has the DB_URL, USER and PASS
	 * and return it as a array of Strings with the same order ([DB_URL, USER , PASS])
	 */
	private String[] loadDefinitions() {
		String[] data = new String[3];
		try(Scanner s = new Scanner(new File("WebContent/resources/definitions.txt"))){
			
			while(s.hasNext()) {
				String[] lineSplited = s.nextLine().split(" = ");
				switch (lineSplited[0]) {
				case "DB_URL":
					data[0] = lineSplited[1];
					break;
				case "USER":
					data[1] = lineSplited[1];
					break;
				case "PASS":
					data[2] = lineSplited[1];
					break;
				default:
					break;
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return data;
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
	 * Suggestion: Use now() on query instead
	 */
	public String getSqlTime() {
		return new Timestamp(new Date().getTime()).toString();
	}


	//TODO prevent SQL Injection Attack !IMPORTATNT


	/**
	 * This method returns the result Set of the query
	 * Select *  from users where email = ? and password = ?
	 * After using ResultSet use the method .close() to avoid connection problems 
	 * @throws SQLException
	 */

	public ResultSet loginQuery(String email, String password) throws SQLException {
		PreparedStatement stm= dbConnection.prepareStatement("Select *  from users where email = ? and password = ?");  
		stm.setString(1,email);				//1 specifies the first parameter in the query  
		stm.setString(2,password);			//2 specifies the Second parameter in the query  
		return stm.executeQuery();
	}


	/**
	 *Inserts a new user on Data Base
	 *Query:
	 *"INSERT INTO users (email, password, isAdmin, isMember,last_log,frist_name,last_name)"+
	 *"VALUES (?,?,false,false,now(),?,?);"
	 *@throws SQLException
	 */
	public void signInQuery(String email, String password, String fname, String lname) throws SQLException {
		PreparedStatement stm= dbConnection.prepareStatement( 
				"INSERT INTO users (email, password, isAdmin, isMember,last_log,frist_name,last_name)"+
				"VALUES (?,?,false,false,now(),?,?);");

		stm.setString(1,email);				
		stm.setString(2,password);			
		stm.setString(3,fname);				 
		stm.setString(4,lname);
		stm.executeUpdate();
		stm.close();
	}


	/**
	 * return true if there is an user with the email given
	 * @throws SQLException 
	 */
	public boolean userExist(String email) throws SQLException {
		PreparedStatement stm = dbConnection.prepareStatement( "Select *  from users where email = ? ");
		stm.setString(1,email);			
		boolean exists = stm.executeQuery().next();
		stm.close();
		return exists;
	}



	//TODO Create your own preparedStatment

}
