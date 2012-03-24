package com.project.dao.impl;

import java.sql.SQLException;

import com.project.dao.AbstractDao;
import com.project.dao.CreateTablesForUser;

public class CreateTablesForUserImpl extends AbstractDao
	implements CreateTablesForUser
{	
	public boolean createUserAddressBook(String username){
		
		System.out.println("In Function : CreateTablesForUserImpl.createUserAddressBook()");
		try{
			System.out.println("In Try Block of Function : CreateTablesForUserImpl.createUserAddressBook()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DROP TABLE IF EXISTS "+username+"_Address_Book";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "CREATE TABLE "+username+"_Address_Book (first_name VARCHAR(20) not null,last_name VARCHAR(20),phone1 VARCHAR(20) primary key not null)";
		    statement.executeUpdate(sql_stmt);
		    
		    return true;
		}
		catch(SQLException se){	
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserAddressBook() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserAddressBook() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createUserGroups(String username){
		
		System.out.println("In Function : CreateTablesForUserImpl.createUserGroups()");
		try{
			System.out.println("In Try Block of Function : CreateTablesForUserImpl.createUserGroups()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DROP TABLE IF EXISTS "+username+"_Group_Users";
			statement.executeUpdate(sql_stmt);
			sql_stmt= "DROP TABLE IF EXISTS "+username+"_Group_Transactions";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "DROP TABLE IF EXISTS "+username+"_Groups";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "CREATE TABLE "+username+"_Groups (group_name VARCHAR(20) primary key not null,group_description text)";
		    statement.executeUpdate(sql_stmt);
		    
		    return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserGroups() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserGroups() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createUserGroupUsers(String username){
		
		System.out.println("In Function : CreateTablesForUserImpl.createUserGroupUsers()");
		try{
			System.out.println("In Try Block of Function : CreateTablesForUserImpl.createUserGroupUsers()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DROP TABLE IF EXISTS "+username+"_Group_Users";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "CREATE TABLE "+username+"_Group_Users (group_name VARCHAR(20) not null,username VARCHAR(20) not null,primary key (group_name,username),FOREIGN KEY (group_name) REFERENCES "+username+"_Groups(group_name),FOREIGN KEY (username) REFERENCES Accounts(username))";
		    statement.executeUpdate(sql_stmt);
		    
		    return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserGroupUsers() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserGroupUsers() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createUserGroupTransactions(String username){
		
		System.out.println("In Function : CreateTablesForUserImpl.createUserGroupTransactions()");
		try{
			System.out.println("In Try Block of Function : CreateTablesForUserImpl.createUserAddressBook()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DROP TABLE IF EXISTS "+username+"_Group_Transactions";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "CREATE TABLE "+username+"_Group_Transactions (group_name VARCHAR(20) not null,transaction_id VARCHAR(10) not null,primary key (group_name,transaction_id),FOREIGN KEY(group_name) REFERENCES "+username+"_Groups(group_name),FOREIGN KEY(transaction_id) REFERENCES "+username+"_Transactions(transaction_id))";
		    statement.executeUpdate(sql_stmt);
		    return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserGroupTransactions() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserGroupTransactions() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createUserTransactions(String username){
		
		System.out.println("In Function : CreateTablesForUserImpl.createUserTransactions()");
		try{
			System.out.println("In Try Block of Function : CreateTablesForUserImpl.createUserTransactions()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DROP TABLE IF EXISTS "+username+"_Group_Transactions";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "DROP TABLE IF EXISTS "+username+"_Transactions";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "CREATE TABLE "+username+"_Transactions (transaction_id VARCHAR(10) primary key not null,sender VARCHAR(20) not null,receiver VARCHAR(20) not null,date_of_transaction Date not null,amount float(7,2) not null,tag_list VARCHAR(50),description text,FOREIGN KEY(sender) REFERENCES Accounts(username),FOREIGN KEY(receiver) REFERENCES Accounts(username))";
		    statement.executeUpdate(sql_stmt);
		    return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserTransactions() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserTransactions() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createUserAppUsers(String username){
		
		System.out.println("In Function : CreateTablesForUserImpl.createUserAppUsers()");
		try{
			System.out.println("In Try Block of Function : createUserAppUsers()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DROP TABLE IF EXISTS "+username+"App_Users";
			statement.executeUpdate(sql_stmt);
			sql_stmt = "CREATE TABLE "+username+"App_Users (app_username VARCHAR(20) primary key not null,FOREIGN KEY (app_username) REFERENCES Accounts(username))";
		    statement.executeUpdate(sql_stmt);
		    return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserAppUsers() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : CreateTablesForUserImpl.createUserAppUsers() for Exception");
			e.printStackTrace();
			return false;
		}
	}
}