package com.project.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.dao.AbstractDao;
import com.project.dao.AccountDao;
import com.project.dom.Account;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.AllAccountsNotFoundException;
import com.project.manager.impl.AccountManagerImpl;

public class AccountDaoImpl
	extends AbstractDao implements AccountDao
{
	
	AccountManagerImpl accManagerImpl = new AccountManagerImpl();
	
	public Account getByUsername(String userName) 
	{
		System.out.println("In Function : AccountDaoImpl.getByUsername()");
		Account returnAccount = new Account();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM Accounts where username = "+ userName;
			ResultSet result_set = statement.executeQuery(sql_stmt);
			
			int size=0;
			if(result_set!=null){
				result_set.beforeFirst();
				result_set.last();
				size = result_set.getRow();
			}
			result_set.beforeFirst();
			if(size == 0){
				return null;
			}
			result_set.next();
			returnAccount = accManagerImpl.createAccount(result_set.getString("username"),
					result_set.getString("user_password"),
					result_set.getString("first_name"),
					result_set.getString("last_name"),
					result_set.getDate("date_of_birth"),
					result_set.getDate("date_of_creation"),
					result_set.getDate("last_access"),
					result_set.getString("user_id"),
					result_set.getString("gender"),
					result_set.getString("phone1"));
				
			
			result_set.close();
			statement.close();
			conn.close();
			return returnAccount;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public Account getByUserId(String userId) 
	{	
		System.out.println("In Function : AccountDaoImpl.getByUserId()");
		Account returnAccount = new Account();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.getByUserId()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM Accounts where user_id = "+ userId;
			ResultSet result_set = statement.executeQuery(sql_stmt);
			
			int size=0;
			if(result_set!=null){
				result_set.beforeFirst();
				result_set.last();
				size = result_set.getRow();
			}
			if(size == 0){
				return null;
			}
			result_set.beforeFirst();
			result_set.next();
			returnAccount = accManagerImpl.createAccount(result_set.getString("username"),
											result_set.getString("user_password"),
											result_set.getString("first_name"),
											result_set.getString("last_name"),
											result_set.getDate("date_of_birth"),
											result_set.getDate("date_of_creation"),
											result_set.getDate("last_access"),
											result_set.getString("user_id"),
											result_set.getString("gender"),
											result_set.getString("phone1"));
			
			
			result_set.close();
			statement.close();
			conn.close();
			return returnAccount;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getByUserId() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getByUserId() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public Account getByPhoneNo(String phone1){
		
		System.out.println("In Function : AccountDaoImpl.getByPhoneNo()");
		Account returnAccount = new Account();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.getByPhoneNo()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM Accounts where phone1 = "+ phone1;
			ResultSet result_set = statement.executeQuery(sql_stmt);
			int size=0;
			if(result_set!=null){
				result_set.beforeFirst();
				result_set.last();
				size = result_set.getRow();
			}
			if(size == 0){
				return null;
			}
			result_set.beforeFirst();
			result_set.next();
			returnAccount = accManagerImpl.createAccount(result_set.getString("username"),
					result_set.getString("user_password"),
					result_set.getString("first_name"),
					result_set.getString("last_name"),
					result_set.getDate("date_of_birth"),
					result_set.getDate("date_of_creation"),
					result_set.getDate("last_access"),
					result_set.getString("user_id"),
					result_set.getString("gender"),
					result_set.getString("phone1"));
				
			result_set.close();
			statement.close();
			conn.close();
			return returnAccount;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getByPhoneNo() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getByPhoneNo() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Account> getAll(){
		
		System.out.println("In Function : AccountDaoImpl.getAll()");
		Account returnAccount = new Account();
		ArrayList<Account> returnAccountList = new ArrayList();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.getAll()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM Accounts";
			ResultSet result_set = statement.executeQuery(sql_stmt);
			int size=0;
			if(result_set!=null){
				result_set.beforeFirst();
				result_set.last();
				size = result_set.getRow();
			}
			if(size == 0){
				return null;
			}
			result_set.beforeFirst();
			
			while(result_set.next()){
				returnAccount = accManagerImpl.createAccount(result_set.getString("username"),
						result_set.getString("user_password"),
						result_set.getString("first_name"),
						result_set.getString("last_name"),
						result_set.getDate("date_of_birth"),
						result_set.getDate("date_of_creation"),
						result_set.getDate("last_access"),
						result_set.getString("user_id"),
						result_set.getString("gender"),
						result_set.getString("phone1"));
				
				returnAccountList.add(returnAccount);
			}
			
			
			result_set.close();
			statement.close();
			conn.close();
			return returnAccountList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getAll() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getAll() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addNewAccount(Account account){
		
		System.out.println("In Function : AccountDaoImpl.addNewAccount()");
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.addNewAccount()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("hello again in accountDao");
		    System.out.println("Creating a PreparedStatement for Insert");
		    PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO Accounts(username,user_password,first_name,last_name,date_of_birth,date_of_creation,last_access,user_id,gender,phone1) VALUES(?,?,?,?,?,?,?,?,?,?)");
		    sql_stmt.setString(1, account.getUsername());
		    sql_stmt.setString(2, account.getPassword());
		    sql_stmt.setString(3, account.getFirstName());
		    sql_stmt.setString(4, account.getLastName());
		    sql_stmt.setString(8, account.getUserId());
		    sql_stmt.setString(9, account.getGender());
		    sql_stmt.setString(10, account.getPhone1());
		    sql_stmt.setDate(5, new java.sql.Date(account.getDateOfBirth().getTime()));
		    sql_stmt.setDate(6, new java.sql.Date(account.getDateOfCreation().getTime()));
		    sql_stmt.setDate(7, new java.sql.Date(account.getLastAccess().getTime()));
			
			sql_stmt.executeUpdate();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.addNewAccount() for SQLException");
			System.out.println("hello again in accountDao 2");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.addNewAccount() for Exception");
			System.out.println("hello again in accountDao 3");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean deleteAccount(Account account){
		
		System.out.println("In Function : AccountDaoImpl.deleteAccount()");
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.deleteAccount()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "Delete from Accounts"+"where username = "+account.getUsername();
			statement.executeUpdate(sql_stmt);
		
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.deleteAccount() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.deleteAccount() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean modifyLastAccess(Account account){
		
		System.out.println("In Function : AccountDaoImpl.modifyLastAccess()");
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.modifyLastAccess()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt = "UPDATE TABLE Accounts SET last_access = now() WHERE username = "+account.getUsername();
			statement.executeUpdate(sql_stmt);

			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.modifyLastAccess() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.modifyLastAccess() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Account> getAccountsByUsername(String[] usernames) throws AllAccountsNotFoundException
	{	
		System.out.println("In Function : AccountDaoImpl.getAccountsByUsername()");
		ArrayList<Account> accountsReturned =  new ArrayList<Account>();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.getAccountsByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			for(int i=0;i<usernames.length;i++){
				String sql_stmt = "SELECT * FROM Accounts WHERE username = "+usernames[i];
				ResultSet result_set = statement.executeQuery(sql_stmt);
				
				int resultSetSize = 0;
				if(result_set != null){
					result_set.beforeFirst();
					result_set.last();
					resultSetSize = result_set.getRow();
				}
				if(resultSetSize == 0){
					throw new AllAccountsNotFoundException("Account with Username : "+usernames[i]+" Not Found!!");
				}
				result_set.beforeFirst();
				result_set.next();
				Account account = accManagerImpl.createAccount(result_set.getString("username"),
						result_set.getString("user_password"),
						result_set.getString("first_name"),
						result_set.getString("last_name"),
						result_set.getDate("date_of_birth"),
						result_set.getDate("date_of_creation"),
						result_set.getDate("last_access"),
						result_set.getString("user_id"),
						result_set.getString("gender"),
						result_set.getString("phone1"));
				accountsReturned.add(account);
			}
			
			statement.close();
			conn.close();
			return accountsReturned;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getAccountsByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.getAccountsByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
}