package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDao
{
	protected String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	//Database credentials
	protected String username="root";
	protected String password="160488";
	
	protected String DB_URL = "jdbc:mysql://localhost/SpringOne";
	//getting connection and statement hooks
	protected Connection conn = null;
	protected Statement statement = null;
	
	public Connection getConnectionForUser(String DB_URL){
		Connection connection = null;
		try{
			Class.forName(JDBC_DRIVER);
		    //STEP 3: Open a connection
		    System.out.println("Connecting to database...");
		    connection = DriverManager.getConnection(DB_URL,username,password);
		}
		catch(SQLException se){
			//handle excpetion if required for jdbc
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}
}