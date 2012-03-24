package com.project.dao.impl;

import java.sql.SQLException;

import com.project.dao.AbstractDao;
import com.project.dao.CreateUniversalTables;

public class CreateUniversalTablesImpl extends AbstractDao
	implements CreateUniversalTables
{	
	public boolean createDatabase(){
		try{
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt="CREATE DATABASE SpringOne";
			statement.executeUpdate(sql_stmt);
		}
		catch(SQLException se){
			//handle excpetion if required for jdbc
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean createAccountsTable(){
		
		try{
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "CREATE TABLE Accounts (String username,String password,String firstName,String lastName,Date dateOfBirth,Date dateOfCreation,Date lastAccess,String userId,String sex,String phone)";
		    statement.executeUpdate(sql_stmt);
		}
		catch(SQLException se){
			//handle excpetion if required for jdbc
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean createSuggestionsTable(){
		
		try{
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "CREATE TABLE Suggestions (String suggestionId,String sender,Date dateOfSuggestion,Number netValue)";
		    statement.executeUpdate(sql_stmt);
		}
		catch(SQLException se){
			//handle excpetion if required for jdbc
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean createSuggestionDetailsTable(){

		try{
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "CREATE TABLE Suggestion_Details (String suggestionId,String receiver,Date dateOfSuggestion,Number netValue)";
		    statement.executeUpdate(sql_stmt);
		}
		catch(SQLException se){
			//handle excpetion if required for jdbc
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean createSuggestionTransactionsTable(){

		try{
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "CREATE TABLE Suggestion_Transactions (String suggestionId,String transactionId)";
		    statement.executeUpdate(sql_stmt);
		}
		catch(SQLException se){
			//handle excpetion if required for jdbc
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}