package com.project.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.dao.AbstractDao;
import com.project.dao.AddressBookDao;
import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Friend;
import com.project.exceptions.FriendNotFoundException;
import com.project.manager.impl.AddressBookManagerImpl;
import com.project.manager.impl.FriendCreatorImpl;

public class AddressBookDaoImpl
	extends AbstractDao implements AddressBookDao 
{
	
	FriendCreatorImpl friendCreatorImpl = new FriendCreatorImpl();
	AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
	AddressBookManagerImpl addressBookManagerImpl = new AddressBookManagerImpl();
	
	public ArrayList<Friend> getAll(Account account){
		
		System.out.println("In Function : AddressBookDaoImpl.getAll()");
		String queried_username = account.getUsername();
		ArrayList<Friend> friendList = new ArrayList();
		try{
			System.out.println("Inside Try Block of Function : AddressBookDaoImpl.getAll()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM "+queried_username+"_Address_Book";
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
			
			//extracting the data from the result set
			while(result_set.next()){
				Friend friend = friendCreatorImpl.createFriend(result_set.getString("first_name"),
																result_set.getString("last_name"),
																result_set.getString("phone1"));
				friendList.add(friend);
			}
			
			
			result_set.close();
			statement.close();
			conn.close();
			return friendList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getAll() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getAll() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public Friend getByPhone(Account account, String phone1){
		
		System.out.println("In Function : AddressBookDaoImpl.getByPhone()");
		String queried_username = account.getUsername();
		Friend friend = new Friend();
		try{
			System.out.println("Inside Try Block of Function : AddressBookDaoImpl.getByPhone()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM "+queried_username+"_Address_Book where phone1 = "+phone1;
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
			friend = friendCreatorImpl.createFriend(result_set.getString("first_name"),
					result_set.getString("last_name"),
					result_set.getString("phone1"));
			
			result_set.close();
			statement.close();
			conn.close();
			return friend;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getByPhone() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getByPhone() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Friend> getByFirstName(Account account, String firstName){
		
		System.out.println("In Function : AddressBookDaoImpl.getByFirstName()");
		ArrayList<Friend> friendList = new ArrayList();
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : AddressBookDaoImpl.getByFirstName()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM "+queried_username+"_Address_Book where first_name ="+firstName;
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
			
			//extracting the data from the result set
			while(result_set.next()){
				Friend friend = friendCreatorImpl.createFriend(result_set.getString("first_name"),
																result_set.getString("last_name"),
																result_set.getString("phone1"));
				friendList.add(friend);
			}
			
			
			result_set.close();
			statement.close();
			conn.close();
			return friendList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getByFirstName() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getByFirstName() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Friend> getByLastName(Account account,String lastName){
		
		System.out.println("In Function : AddressBookDaoImpl.getByLastName()");
		ArrayList<Friend> friendList = new ArrayList();
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : AddressBookDaoImpl.getByLastName()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM "+queried_username+"_Address_Book where last_name ="+lastName;
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
			
			//extracting the data from the result set
			while(result_set.next()){
				Friend friend = friendCreatorImpl.createFriend(result_set.getString("first_name"),
																result_set.getString("last_name"),
																result_set.getString("phone1"));
				friendList.add(friend);
			}
			
			
			result_set.close();
			statement.close();
			conn.close();
			return friendList;
		}
		catch(SQLException se){	
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getByLastName() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getByLastName() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addNewFriend(Friend friend,Account account){
		
		System.out.println("In Function : AddressBookDaoImpl.addNewFriend()");
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : AddressBookDaoImpl.addNewFriend()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating a PreparedStatement for Insert");
		    PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO "+queried_username+"_Address_Book(first_name,last_name,phone1) VALUES(?,?,?)");
		    sql_stmt.setString(1, friend.getFirstName());
		    sql_stmt.setString(2, friend.getLastName());
		    sql_stmt.setString(3, friend.getPhone1());
			sql_stmt.executeUpdate();
			
			conn.close();
			return true;
			
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.addNewFriend() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.addNewFriend() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteFriend(Friend friend,Account account) throws FriendNotFoundException
	{	
		System.out.println("In Function : AddressBookDaoImpl.deleteFriend()");
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : AddressBookDaoImpl.deleteFriend()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DELETE FROM "+queried_username+"_Address_Book where phone1 = "+friend.getPhone1();
			int rowsDeleted = statement.executeUpdate(sql_stmt); 
			
			if(rowsDeleted == 0){
				throw new FriendNotFoundException("Friend  "+friend.getFirstName()+" was Not Found!!");
			}
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.deleteFriend() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.deleteFriend() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public AddressBook getAddressBook(Account account){
		
		System.out.println("In Function : AddressBookDaoImpl.getAddressBook()");
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : AddressBookDaoImpl.getAddressBook()");
			conn=getConnectionForUser(DB_URL);
			System.out.println("Creating statement");
			statement = conn.createStatement();
			String sql_stmt,sql_stmt_2;
			sql_stmt = "SELECT * FROM "+queried_username+"_Address_Book";
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
			ArrayList<Friend> friendList = new ArrayList<Friend>();
			while(result_set.next()){
				Friend friend = friendCreatorImpl.createFriend(result_set.getString("first_name"),
						result_set.getString("last_name"),
						result_set.getString("phone1"));
				friendList.add(friend);
			}
			sql_stmt_2 = "SELECT * FROM "+queried_username+"_App_Users";
			ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
			ArrayList<Account> appUsersList = new ArrayList<Account>();
			while(result_set_2.next()){
				Account appUserAccount = accountDaoImpl.getByUsername(result_set_2.getString("username"));
				appUsersList.add(appUserAccount);
			}
			AddressBook userAddressBook = addressBookManagerImpl.createAddressBook(appUsersList, friendList);
			
			result_set.close();
			statement.close();
			conn.close();
			
			return userAddressBook;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getAddressBook() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AddressBookDaoImpl.getAddressBook() for Exception");
			e.printStackTrace();
			return null;
		}
	}
}