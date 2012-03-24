package com.project.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.project.dao.AbstractDao;
import com.project.dao.GroupDao;
import com.project.dom.Account;
import com.project.dom.AddressBook;
import com.project.dom.Group;
import com.project.dom.Notification;
import com.project.dom.Suggestion;
import com.project.dom.Transaction;
import com.project.exceptions.FriendNotFoundException;
import com.project.exceptions.GroupAlreadyPresentException;
import com.project.exceptions.GroupNotFoundException;
import com.project.exceptions.TransactionNotFoundException;
import com.project.manager.User;
import com.project.manager.impl.AccountManagerImpl;
import com.project.manager.impl.GroupCreatorImpl;
import com.project.manager.impl.UserManagerImpl;

public class GroupDaoImpl
	extends AbstractDao implements GroupDao
{
	AccountManagerImpl accountManagerImpl = new AccountManagerImpl();
	AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
	TransactionDaoImpl transactionDaoImpl = new TransactionDaoImpl();
	SuggestionDaoImpl suggestionDaoImpl = new SuggestionDaoImpl();
	AddressBookDaoImpl addressBookDaoImpl = new AddressBookDaoImpl(); 
	UserManagerImpl userManagerImpl = new UserManagerImpl();
	GroupCreatorImpl groupCreatorImpl = new GroupCreatorImpl();
	
	public String getGroupDescription(Account account, String groupName){
		
		System.out.println("In Function : GroupDaoImpl.getGroupDescription()");
		String groupDescription = null;
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.getGroupDescription()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT group_description FROM "+queried_username+"_Groups";
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
			groupDescription = result_set.getString("group_description");
			
			result_set.close();
			statement.close();
			conn.close();
			return groupDescription;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.getGroupDescription() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.getGroupDescription() for Exception");
			e.printStackTrace();
			return  null;
		}
	}
	
	public ArrayList<User> getUsers(Account account, String groupName){
		
		System.out.println("In Function : GroupDaoImpl.getUsers()");
		String queried_username = account.getUsername();
		ArrayList<User> returnUserList = new ArrayList<User>();
		
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.getUsers()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			
			sql_stmt = "SELECT username FROM "+queried_username+"_Group_Users where group_name = "+groupName;
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
				Account userAccount = accountDaoImpl.getByUsername(result_set.getString("username"));
			    ArrayList<Group> userGroups = this.getGroups(userAccount);
			    ArrayList<Transaction> userTransactions = transactionDaoImpl.getTransactions(userAccount);
			    //NOTE : Including those suggestions that have been sent by this user. To be changed if this is not the required list.
			    ArrayList<Suggestion> userSuggestions = suggestionDaoImpl.getBySender(userAccount);
			    AddressBook userAddressBook = addressBookDaoImpl.getAddressBook(userAccount);
			    //NOTE : Notification is also unclear. Sending an empty Notification List for now
			    ArrayList<Notification> userNotifications = new ArrayList<Notification>();
			    
			    User userToAdd = userManagerImpl.createUser(userAccount,userGroups,userTransactions,userSuggestions,userAddressBook,userNotifications);
			    
			    returnUserList.add(userToAdd);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return returnUserList;
			
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.getUsers() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.getUsers() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addGroup(Group group) throws GroupAlreadyPresentException
	{	
		System.out.println("In Function : GroupDaoImpl.addGroup()");
		String groupName = group.getGroupName();
		String groupDescription = group.getGroupDescription();
		String queried_user = group.getAccount().getUsername();
		System.out.println(groupName);
		System.out.println(groupDescription);
		System.out.println(queried_user);
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.addGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
		    //String sql_check = "SELECT group_name from "+queried_user+"_Groups WHERE group_name = '"+groupName+"'";
		    PreparedStatement sql_stmt = conn.prepareStatement("select group_name from "+queried_user+"_Groups where group_name=?");
		    sql_stmt.setString(1, groupName);
		    ResultSet rs = sql_stmt.executeQuery();
		    rs.beforeFirst();
		    rs.last();
		    int rowsReturned=rs.getRow();
		    if(rowsReturned != 0){
		    	throw new GroupAlreadyPresentException("Group "+groupName+" Already Present!!");
		    }
		    sql_stmt = conn.prepareStatement("INSERT INTO "+queried_user+"_Groups(group_name,group_description) VALUES(?,?)");
		    sql_stmt.setString(1, groupName);
		    sql_stmt.setString(2, groupDescription);
			sql_stmt.executeUpdate();

			sql_stmt.close();
			conn.close();
			return true;
			
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean modifyGroup(Group group){
		
		System.out.println("In Function : GroupDaoImpl.addNewAccount()");
		String groupDescription = group.getGroupDescription();
		String queried_user = group.getAccount().getUsername();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.modifyGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "UPDATE TABLE"+queried_user+"_Groups SET group_description = "+groupDescription;
			statement.executeUpdate(sql_stmt);
			
			statement.close();
			conn.close();
			
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.modifyGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.modifyGroup() for Exception");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteGroup(Group group){
		
		System.out.println("In Function : GroupDaoImpl.deleteGroup()");
		String groupName = group.getGroupName();
		Account account = group.getAccount();
		String queried_user = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.deleteGroup()");
			String sql_stmt;
			
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
		    //Now delete from Groups_Users Table as well.
			sql_stmt = "DELETE FROM TABLE "+queried_user+"_Group_Users where group_name = "+groupName;
			statement.executeUpdate(sql_stmt);
			
			//deleting from Groups_Transactions table as well
			sql_stmt = "DELETE FROM TABLE "+queried_user+"_Group_Transactions where group_name = "+groupName;
			statement.executeUpdate(sql_stmt);
			
			//deleting from Groups Table
			sql_stmt = "DELETE FROM TABLE "+queried_user+"_Groups WHERE group_name = "+groupName;
			statement.executeUpdate(sql_stmt);
			
			
			
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addFriendsToGroup(ArrayList<Account> accounts,Group group) throws GroupNotFoundException
	{	
		System.out.println("In Function : GroupDaoImpl.addFriendsToGroup()");
		String groupName = group.getGroupName();
		String queried_user = group.getAccount().getUsername();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.addFriendsToGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
		    String sql_check = "Select group_name FROM "+queried_user+"_Groups WHERE group_name ="+groupName;
		    int rowsReturned = statement.executeUpdate(sql_check);
		    if(rowsReturned == 0){
		    	throw new GroupNotFoundException("Group "+groupName+" Not Found!!");
		    }
			Iterator<Account> accounts_iterator = accounts.iterator();
			while(accounts_iterator.hasNext()){
				PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO "+queried_user+"_Group_Users(group_name,username) values(?,?)");
				sql_stmt.setString(1, groupName);
				sql_stmt.setString(2, accounts_iterator.next().getUsername());
				sql_stmt.executeUpdate();
			}
			
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addFriendsToGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addFriendsToGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addFriendToGroup(Account friendAccount,Group group){
		
		System.out.println("In Function : GroupDaoImpl.addFriendToGroup()");
		String groupName = group.getGroupName();
		Account account = group.getAccount();
		String queried_user = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.addFriendToGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
			PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO "+queried_user+"_Group_Users(group_name,username) values(?,?)");
			sql_stmt.setString(1, groupName);
			sql_stmt.setString(2, friendAccount.getUsername());
			sql_stmt.executeUpdate();
			
			sql_stmt.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addFriendToGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addFriendToGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteFriendsFromGroup(ArrayList<Account> accounts,Group group) throws GroupNotFoundException,FriendNotFoundException
	{	
		System.out.println("In Function : GroupDaoImpl.deleteFriendsFromGroup()");
		String groupName = group.getGroupName();
		Account account = group.getAccount();
		String queried_user = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.deleteFriendsFromGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    String sql_check = "Select group_name FROM "+queried_user+"_Groups WHERE group_name = "+groupName;
		    int rowsReturned = statement.executeUpdate(sql_check);
		    if(rowsReturned == 0){
		    	throw new GroupNotFoundException("The Group "+groupName+"was Not Found!!");
		    }
			String sql_stmt;
			Iterator<Account> accounts_iterator = accounts.iterator();
			while(accounts_iterator.hasNext()){
				String friendUsername = accounts_iterator.next().getUsername();
				sql_stmt = "DELETE FROM "+queried_user+"_Group_Users WHERE group_name = "+groupName+" AND username = "+friendUsername;
				int rowReturned = statement.executeUpdate(sql_stmt);
				if(rowReturned == 0){
					throw new FriendNotFoundException("The friend "+friendUsername+" was Not Found!!");
				}
			}
			
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteFriendsFromGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteFriendsFromGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteFriendFromGroup(Account friendAccount,Group group){
		
		System.out.println("In Function : GroupDaoImpl.deleteFriendFromGroup()");
		String groupName = group.getGroupName();
		Account account = group.getAccount();
		String queried_user = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.deleteFriendFromGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "DELETE FROM "+queried_user+"_Group_Users WHERE group_name = "+groupName+" AND username = "+friendAccount.getUsername();
			statement.executeUpdate(sql_stmt);

			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteFriendFromGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteFriendFromGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Group> getGroups(Account account){
		
		System.out.println("In Function : GroupDaoImpl.getGroups()");
		String queried_user = account.getUsername();
		ArrayList<Group> groupList = new ArrayList();
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.getGroups()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			// We need groupName,groupDescription,transactionIds,users,account.
			String sql_stmt,sql_stmt_2,sql_stmt_3;
			
			sql_stmt = "SELECT group_name,group_description FROM "+queried_user+"_Groups";
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
				// now for each groupName I have to create a group object
				//Next get the Transactions
				
				sql_stmt_2 = "SELECT transaction_id from "+queried_user+"_Group_Transactions where group_name = "+result_set.getString("group_name");
				ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
				//I have got a list of transaction Id's
				ArrayList<String> transactionIds = new ArrayList<String>();
				while(result_set_2.next()){
					transactionIds.add(result_set_2.getString("transaction_id"));
				}
				//Now I have to add all users
				
				sql_stmt_3 = "SELECT username FROM "+queried_user+"_Group_Users WHERE group_name = "
							+result_set.getString("group_name");
				ResultSet result_set_3 = statement.executeQuery(sql_stmt_3);
				ArrayList<Account> users = new ArrayList<Account>();
				while(result_set_3.next()){
					Account accountOfUser = accountDaoImpl.getByUsername(result_set_3.getString("username"));
					users.add(accountOfUser);
				}
				//Now I have got all the details I just have to create a group Object
				Group group = groupCreatorImpl.createGroup(result_set.getString("group_name"), 
														result_set.getString("group_description"), 
														transactionIds, users, account);
				groupList.add(group);
			}
			
			statement.close();
			conn.close();
			return groupList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.getGroups() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.getGroups() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean addTransactionsToGroup(ArrayList<Transaction> transactions, Group group) throws GroupNotFoundException
	{	
		System.out.println("In Function : GroupDaoImpl.addTransactionsToGroup()");
		Account account = group.getAccount();
		String queried_user = account.getUsername();
		String groupName = group.getGroupDescription();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.addTransactionsToGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    String sql_check = "SELECT group_name from "+queried_user+"_Groups WHERE group_name ="+groupName;
		    int rowsReturned = statement.executeUpdate(sql_check);
		    if(rowsReturned == 0){
		    	throw new GroupNotFoundException("Group "+groupName+" was Not Found!!");
		    }
			Iterator<Transaction> trans_iterator = transactions.iterator();
			while(trans_iterator.hasNext()){
				PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO "+queried_user+"_Group_Transactions(group_name,transaction_id) VALUES(?,?)");
				sql_stmt.setString(1, groupName);
				sql_stmt.setString(2, trans_iterator.next().getTransactionId());
				}
			
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.addTransactionsToGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : AccountDaoImpl.addTransactionsToGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addTransactionToGroup(Group group, Transaction transaction){
		
		System.out.println("In Function : AccountDaoImpl.addTransactionToGroup()");
		String queried_user = group.getAccount().getUsername();
		String groupName = group.getGroupDescription();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.addTransactionToGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
			PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO "+queried_user+"_Group_Transactions(group_name,transaction_id) VALUES(?,?)");
			sql_stmt.setString(1, groupName);	
			sql_stmt.setString(2, transaction.getTransactionId());
			
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addTransactionToGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.addTransactionToGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteTransactionFromGroup(Group group, String transactionId) throws GroupNotFoundException,TransactionNotFoundException
	{	
		System.out.println("In Function : GroupDaoImpl.deleteTransactionFromGroup()");
		String queried_user = group.getAccount().getUsername();
		String groupName = group.getGroupName();
		String sql_stmt;
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.deleteTransactionFromGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
		    String sql_check = "SELECT group_name from "+queried_user+"_Groups WHERE group_name ="+groupName;
		    int rowsReturned = statement.executeUpdate(sql_check);
		    if(rowsReturned == 0){
		    	throw new GroupNotFoundException("Group "+groupName+" was Not Found!!");
		    }
			sql_stmt = "DELETE FROM "+queried_user+"_Group_Transactions WHERE transaction_id = "+transactionId;
			int rowReturned = statement.executeUpdate(sql_stmt);
			if(rowReturned == 0){
				throw new TransactionNotFoundException("Transaction with ID "+transactionId+" was Not Found!!");
			}
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteTransactionFromGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteTransactionFromGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteTransactionsFromGroup(ArrayList<Transaction> transactions, Group group) throws GroupNotFoundException,TransactionNotFoundException
	{	
		System.out.println("In Function : GroupDaoImpl.deleteTransactionsFromGroup()");
		String queried_user = group.getAccount().getUsername();
		String groupName = group.getGroupName();
		String sql_stmt;
		try{
			System.out.println("Inside Try Block of Function : GroupDaoImpl.deleteTransactionsFromGroup()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
		    String sql_check = "SELECT group_name from "+queried_user+"_Groups WHERE group_name ="+groupName;
		    int rowsReturned = statement.executeUpdate(sql_check);
		    if(rowsReturned == 0){
		    	throw new GroupNotFoundException("Group "+groupName+" was Not Found!!");
		    }
			Iterator<Transaction> trans_iterator = transactions.iterator();
			while(trans_iterator.hasNext()){
				String transactionId = trans_iterator.next().getTransactionId();
				sql_stmt = "DELETE FROM "+queried_user+"_Group_Transactions WHERE transaction_id = "+transactionId;
				int rowReturned = statement.executeUpdate(sql_stmt);
				if(rowReturned == 0){
					throw new TransactionNotFoundException("Transaction with ID "+transactionId+" was Not Found!!");
				}
				}
			
			statement.close();
			conn.close();
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteTransactionsFromGroup() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : GroupDaoImpl.deleteTransactionsFromGroup() for Exception");
			e.printStackTrace();
			return false;
		}
	}
}