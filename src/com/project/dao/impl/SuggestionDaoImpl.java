package com.project.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.project.dao.AbstractDao;
import com.project.dao.SuggestionDao;
import com.project.dom.Account;
import com.project.dom.Notification;
import com.project.dom.Suggestion;
import com.project.dom.SuggestionReceiver;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.SuggestionNotFoundException;
import com.project.manager.impl.SuggestionCreatorImpl;

public class SuggestionDaoImpl
	extends AbstractDao implements SuggestionDao
{
	TransactionDaoImpl transactionDaoImpl = new TransactionDaoImpl();
	AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
	SuggestionReceiverDaoImpl suggestionReceiverDaoImpl = new SuggestionReceiverDaoImpl();
	SuggestionCreatorImpl suggestionCreatorImpl = new SuggestionCreatorImpl();
	
	public ArrayList<Suggestion> getBySender(Account account){
		
		System.out.println("In Function : SuggestionDaoImpl.getBySender()");
		String usernameSender = account.getUsername();
		ArrayList<Suggestion> suggestionList = new ArrayList<Suggestion>();
		try{
			System.out.println("Inside Try Block of Function : AccountDaoImpl.getBySender()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
			// We need sugegstionId,sender,receivers,transactionIds,dateOfSuggestion,netValue
			String sql_stmt,sql_stmt_2,sql_stmt_3;
			sql_stmt = "SELECT * FROM Suggestions where sender = "+usernameSender;
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
			//we have got a list of suggestion Id's corresponding to a particular sender. We need to create a suggestion object for each of them
			while(result_set.next()){
				Account senderAccount = accountDaoImpl.getByUsername(usernameSender);
				//we have sender account
				String suggestionId = result_set.getString("suggestion_id");
				//we have suggestionId
				//create Suggestion object corresponding to this ID. Have to extract from the manager
				sql_stmt_2 = "SELECT transaction_id from Suggestion_Transactions WHERE suggestion_id = "+suggestionId;
				ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
				ArrayList<String> transactionIdList = new ArrayList<String>();
				while(result_set_2.next()){
					transactionIdList.add(result_set_2.getString("transaction_id"));
				}
				//All transactionIds have been added
				
				sql_stmt_3 = "SELECT receiver,acceptance_status,pending_status from Suggestion_Details where suggestion_id ="
										+suggestionId;
				ResultSet result_set_3 = statement.executeQuery(sql_stmt_3);
				ArrayList<SuggestionReceiver> suggestionReceiverList = new ArrayList<SuggestionReceiver>();
				while(result_set_3.next()){
					Account receiverAccount = accountDaoImpl.getByUsername(result_set_3.getString("receiver"));
					SuggestionReceiver suggestionReceiver = suggestionReceiverDaoImpl.createSuggestionReceiver(receiverAccount, 
																						result_set_3.getBoolean("acceptance_status"), 
																						result_set_3.getBoolean("pending_status"));
					suggestionReceiverList.add(suggestionReceiver);
				}
				Suggestion suggestion = suggestionCreatorImpl.createSuggestion(suggestionId, 
																			senderAccount, 
																			suggestionReceiverList, 
																			transactionIdList, 
																			result_set.getDate("date_of_suggestion"), 
																			result_set.getFloat("net_value"));
				suggestionList.add(suggestion);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return suggestionList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getBySender() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getBySender() for Exception");
			e.printStackTrace();
			return null;
		}	
	}
	
	public ArrayList<Suggestion> getByPendingStatus(boolean pendingStatus){
		
		System.out.println("In Function : SuggestionDaoImpl.getByPendingStatus()");
		ArrayList<Suggestion> suggestionList = new ArrayList<Suggestion>();
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.getByPendingStatus()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			// We need sugegstionId,sender,receivers,transactionIds,dateOfSuggestion,netValue
			String sql_stmt,sql_stmt_2,sql_stmt_3,sql_stmt_4;
			sql_stmt = "SELECT suggestion_id FROM Suggestions_Details where pending_status = "+pendingStatus;
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
			//we have got a list of suggestion Id's corresponding to a particular sender. We need to create a suggestion object for each of them
			while(result_set.next()){
				String suggestionId = result_set.getString("suggestion_id");
				//we have suggestionId
				sql_stmt_2 = "Select * from Suggestions WHERE suggestion_id = "+suggestionId;
				ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
				Account senderAccount = accountDaoImpl.getByUsername(result_set_2.getString("sender"));
				//we have sender account
				//create Suggestion object corresponding to this ID. Have to extract from the manager
				sql_stmt_3 = "SELECT transaction_id from Suggestion_Transactions WHERE suggestion_id = "+suggestionId;
				ResultSet result_set_3 = statement.executeQuery(sql_stmt_3);
				ArrayList<String> transactionIdList = new ArrayList<String>();
				while(result_set_3.next()){
					transactionIdList.add(result_set_3.getString("transaction_id"));
				}
				//All transactionIds have been added
				sql_stmt_4 = "SELECT receiver,acceptance_status,pending_status from Suggestion_Details where suggestion_id ="
										+suggestionId;
				ResultSet result_set_4 = statement.executeQuery(sql_stmt_4);
				ArrayList<SuggestionReceiver> suggestionReceiverList = new ArrayList<SuggestionReceiver>();
				while(result_set_4.next()){
					Account receiverAccount = accountDaoImpl.getByUsername(result_set_4.getString("receiver"));
					SuggestionReceiver suggestionReceiver = suggestionReceiverDaoImpl.createSuggestionReceiver(receiverAccount, 
																						result_set_4.getBoolean("acceptance_status"), 
																						result_set_4.getBoolean("pending_status"));
					suggestionReceiverList.add(suggestionReceiver);
				}
				Suggestion suggestion = suggestionCreatorImpl.createSuggestion(suggestionId, 
																			senderAccount, 
																			suggestionReceiverList, 
																			transactionIdList, 
																			result_set_2.getDate("date_of_suggestion"), 
																			result_set_2.getFloat("net_value"));
				suggestionList.add(suggestion);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return suggestionList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getByPendingStatus() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getByPendingStatus() for Exception");
			e.printStackTrace();
			return null;
		}	
	}
	
	public ArrayList<Suggestion> getByDateOfSuggestion(Date date){
		
		System.out.println("In Function : SuggestionDaoImpl.getByDateOfSuggestion()");
		ArrayList<Suggestion> suggestionList = new ArrayList<Suggestion>();
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.getByDateOfSuggestion()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2,sql_stmt_3;
			sql_stmt = "SELECT * FROM Suggestions where date_of_suggestion = "+date;
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
				String suggestionId = result_set.getString("suggestion_id");
				//we have suggestionId
				Account senderAccount = accountDaoImpl.getByUsername(result_set.getString("sender"));
				//we have sender account
				//create Suggestion object corresponding to this ID. Have to extract from the manager
				
				sql_stmt_2 = "SELECT transaction_id from Suggestion_Transactions WHERE suggestion_id = "+suggestionId;
				ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
				ArrayList<String> transactionIdList = new ArrayList<String>();
				while(result_set_2.next()){
					transactionIdList.add(result_set_2.getString("transaction_id"));
				}
				//All transactionIds have been added
				
				sql_stmt_3 = "SELECT receiver,acceptance_status,pending_status from Suggestion_Details where suggestion_id ="+suggestionId;
				ResultSet result_set_3 = statement.executeQuery(sql_stmt_3);
				ArrayList<SuggestionReceiver> suggestionReceiverList = new ArrayList<SuggestionReceiver>();
				while(result_set_3.next()){
					Account receiverAccount = accountDaoImpl.getByUsername(result_set_3.getString("receiver"));
					SuggestionReceiver suggestionReceiver = suggestionReceiverDaoImpl.createSuggestionReceiver(receiverAccount, 
																						result_set_3.getBoolean("acceptance_status"), 
																						result_set_3.getBoolean("pending_status"));
					suggestionReceiverList.add(suggestionReceiver);
				}
				Suggestion suggestion = suggestionCreatorImpl.createSuggestion(suggestionId, 
																			senderAccount, 
																			suggestionReceiverList, 
																			transactionIdList, 
																			result_set_2.getDate("date_of_suggestion"), 
																			result_set_2.getFloat("net_value"));
				suggestionList.add(suggestion);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return suggestionList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getByDateOfSuggestion() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getByDateOfSuggestion() for Exception");
			e.printStackTrace();
			return null;
		}	
	}
	
	public ArrayList<Suggestion> getByAcceptanceStatus(boolean acceptanceStatus){
		
		System.out.println("In Function : SuggestionDaoImpl.getByAcceptanceStatus()");
		ArrayList<Suggestion> suggestionList = new ArrayList<Suggestion>();
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.getByAcceptanceStatus()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			// We need sugegstionId,sender,receivers,transactionIds,dateOfSuggestion,netValue
			String sql_stmt,sql_stmt_2,sql_stmt_3,sql_stmt_4;
			sql_stmt = "SELECT suggestion_id FROM Suggestions_Details where acceptance_status = "+acceptanceStatus;
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
			//we have got a list of suggestion Id's corresponding to a particular sender. We need to create a suggestion object for each of them
			while(result_set.next()){
				String suggestionId = result_set.getString("suggestion_id");
				//we have suggestionId
				sql_stmt_2 = "Select * from Suggestions WHERE sugegstion_id = "+suggestionId;
				ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
				Account senderAccount = accountDaoImpl.getByUsername(result_set_2.getString("sender"));
				//we have sender account
				//create Suggestion object corresponding to this ID. Have to extract from the manager
				sql_stmt_3 = "SELECT transaction_id from Suggestion_Transactions WHERE suggestion_id = "+suggestionId;
				ResultSet result_set_3 = statement.executeQuery(sql_stmt_3);
				ArrayList<String> transactionIdList = new ArrayList<String>();
				while(result_set_3.next()){
					transactionIdList.add(result_set_3.getString("transaction_id"));
				}
				//All transactionIds have been added
				sql_stmt_4 = "SELECT receiver,acceptance_status,pending_status from Suggestion_Details where suggestion_id ="+suggestionId;
				ResultSet result_set_4 = statement.executeQuery(sql_stmt_4);
				ArrayList<SuggestionReceiver> suggestionReceiverList = new ArrayList<SuggestionReceiver>();
				while(result_set_4.next()){
					Account receiverAccount = accountDaoImpl.getByUsername(result_set_4.getString("receiver"));
					SuggestionReceiver suggestionReceiver = suggestionReceiverDaoImpl.createSuggestionReceiver(receiverAccount, 
																						result_set_4.getBoolean("acceptance_status"), 
																						result_set_4.getBoolean("pending_status"));
					suggestionReceiverList.add(suggestionReceiver);
				}
				Suggestion suggestion = suggestionCreatorImpl.createSuggestion(suggestionId, 
																			senderAccount, 
																			suggestionReceiverList, 
																			transactionIdList, 
																			result_set_2.getDate("date_of_suggestion"), 
																			result_set_2.getFloat("net_value"));
				suggestionList.add(suggestion);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return suggestionList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getByAcceptanceStatus() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getByAcceptanceStatus() for Exception");
			e.printStackTrace();
			return null;
		}	
	}
	
	public ArrayList<SuggestionReceiver> getReceiversPerSuggestion(String suggestionId){
		
		System.out.println("In Function : SuggestionDaoImpl.getReceiversPerSuggestion()");
		ArrayList<SuggestionReceiver> receiverList = new ArrayList<SuggestionReceiver>();
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.getReceiversPerSuggestion()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			sql_stmt = "SELECT * FROM Suggestions_Details where suggestion_id = "+suggestionId;
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
				Account receiverAccount = accountDaoImpl.getByUsername(result_set.getString("sender"));
				SuggestionReceiver suggestionReceiver = suggestionReceiverDaoImpl.createSuggestionReceiver(receiverAccount, 
																					result_set.getBoolean("acceptance_status"), 
																					result_set.getBoolean("pending_status"));
				receiverList.add(suggestionReceiver);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return receiverList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getReceiversPerSuggestion() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getReceiversPerSuggestion() for Exception");
			e.printStackTrace();
			return null;
		}	
	}
	
	public String addSuggestion(Suggestion suggestion){
		
		System.out.println("In Function : SuggestionDaoImpl.addSuggestion()");
		ResultSet result_set_random;
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.addSuggestion()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
			PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO Suggestions(suggestion_id,sender,date_of_suggestion,net_value) VALUES(?,?,?,?)");
			String sql_random = "SELECT CONVERT(SUBSTRING(MD5(RAND()) FROM 1 FOR 10) USING latin1) AS random_id";
			result_set_random = statement.executeQuery(sql_random);
			result_set_random.next();
			sql_stmt.setString(1,result_set_random.getString("random_id"));
			sql_stmt.setString(2,suggestion.getSender().getUsername());
			sql_stmt.setDate(3,new java.sql.Date(suggestion.getDateOfSuggestion().getTime()));
			sql_stmt.setFloat(4,suggestion.getNetValue());
			sql_stmt.executeUpdate();
			
			Iterator<SuggestionReceiver> receiverIterator = suggestion.getReceivers().iterator();
			while(receiverIterator.hasNext()){
				String localUsername = receiverIterator.next().getReceiver().getUsername();
				boolean localAcceptanceStatus = receiverIterator.next().getAcceptanceStatus();
				boolean localPendingStatus = receiverIterator.next().getPendingStatus(); 
				PreparedStatement sql_stmt_2 = conn.prepareStatement("INSERT INTO Suggestion_Details(suggestion_id,sender,date_of_suggestion,net_value) VALUES(?,?,?,?)");
				sql_stmt_2.setString(1,suggestion.getSuggestionId());
				sql_stmt_2.setString(2,localUsername);
				sql_stmt_2.setBoolean(3,localAcceptanceStatus);
				sql_stmt_2.setBoolean(4,localPendingStatus);
				sql_stmt_2.executeUpdate();
			}
			Iterator<String> transactionIterator = suggestion.getTransactionIds().iterator();
			while(transactionIterator.hasNext()){
				PreparedStatement sql_stmt_3 = conn.prepareStatement("INSERT INTO Suggestion_Transactions VALUES(?,?)");
				sql_stmt_3.setString(1,suggestion.getSuggestionId());
				sql_stmt.setString(2,transactionIterator.next());
				sql_stmt_3.executeUpdate();
			}
			return result_set_random.getString("random_id");
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.addSuggestion() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.addSuggestion() for Exception");
			e.printStackTrace();
			return null;
		}	
	}
	
	public boolean removeSuggestion(Suggestion suggestion){
		
		System.out.println("In Function : SuggestionDaoImpl.removeSuggestion()");
		String sql_stmt,sql_stmt_2,sql_stmt_3;
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.removeSuggestion()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
		    sql_stmt_2 = "DELETE FROM Suggestions_Details WHERE suggestion_id = "+suggestion.getSuggestionId();
			statement.executeUpdate(sql_stmt_2);
			sql_stmt_3 = "DELETE FROM Suggestions_Transactions WHERE suggestion_id = "+suggestion.getSuggestionId();
			statement.executeUpdate(sql_stmt_3);
			sql_stmt = "DELETE FROM Suggestions WHERE suggestion_id = "+suggestion.getSuggestionId();
			statement.executeUpdate(sql_stmt);
			
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.removeSuggestion() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.removeSuggestion() for Exception");
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean modifySuggestionReceiver(Notification notification, String propertyName) throws SuggestionNotFoundException
	{	
		System.out.println("In Function : SuggestionDaoImpl.modifySuggestionReceiver()");
		String sql_stmt;
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.modifySuggestionReceiver()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    
		    String sql_check = "SELECT suggestion_id FROM Suggestions WHERE suggestion_id ="+notification.getSuggestionId();
		    int rowsReturned = statement.executeUpdate(sql_check);
		    if(rowsReturned == 0){
		    	throw new SuggestionNotFoundException("Suggestion with ID "+notification.getSuggestionId()+" was Not Found!!");
		    }
			if(propertyName.equals("pending_status")){
				sql_stmt = "UPDATE TABLE Suggestion_Details SET pending_status = "+notification.getPendingStatus()+" WHERE sender = "+notification.getOwnerId();
				statement.executeUpdate(sql_stmt);
			}
			else if(propertyName.equals("acceptance_status")){
				sql_stmt = "UPDATE TABLE Suggestion_Details SET acceptance_status = "+notification.getAcceptanceStatus()+" WHERE sender = "+notification.getOwnerId();
				statement.executeUpdate(sql_stmt);
			}
			return true;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.modifySuggestionReceiver() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.modifySuggestionReceiver() for Exception");
			e.printStackTrace();
			return false;
		}	
	}
	
	public ArrayList<Suggestion> getAll(Account account) throws AccountNotFoundException
	{	
		System.out.println("In Function : SuggestionDaoImpl.getAll()");
		String usernameSender = account.getUsername();
		ArrayList<Suggestion> suggestionList = new ArrayList<Suggestion>();
		try{
			System.out.println("Inside Try Block of Function : SuggestionDaoImpl.getAll()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
		    String sql_check = "SELECT username from Accounts WHERE username ="+account.getUsername();
			int rowsReturned = statement.executeUpdate(sql_check);
			if(rowsReturned ==0){
				throw new AccountNotFoundException("Account with username "+account.getUsername()+" was Not Found!!");
			}
			// We need sugegstionId,sender,receivers,transactionIds,dateOfSuggestion,netValue
			String sql_stmt,sql_stmt_2,sql_stmt_3;
			sql_stmt = "SELECT * FROM Suggestions where sender = "+usernameSender;
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
			//we have got a list of suggestion Id's corresponding to a particular sender. We need to create a suggestion object for each of them
			while(result_set.next()){
				Account senderAccount = accountDaoImpl.getByUsername(usernameSender);
				//we have sender account
				String suggestionId = result_set.getString("suggestion_id");
				//we have suggestionId
				//create Suggestion object corresponding to this ID. Have to extract from the manager
				sql_stmt_2 = "SELECT transaction_id from Suggestion_Transactions WHERE suggestion_id = "+suggestionId;
				ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
				ArrayList<String> transactionIdList = new ArrayList<String>();
				while(result_set_2.next()){
					transactionIdList.add(result_set_2.getString("transaction_id"));
				}
				//All transactionIds have been added
				sql_stmt_3 = "SELECT receiver,acceptance_status,pending_status from Suggestion_Details where suggestion_id ="
										+suggestionId;
				ResultSet result_set_3 = statement.executeQuery(sql_stmt_3);
				ArrayList<SuggestionReceiver> suggestionReceiverList = new ArrayList<SuggestionReceiver>();
				while(result_set_3.next()){
					Account receiverAccount = accountDaoImpl.getByUsername(result_set_3.getString("receiver"));
					SuggestionReceiver suggestionReceiver = suggestionReceiverDaoImpl.createSuggestionReceiver(receiverAccount, 
																						result_set_3.getBoolean("acceptance_status"), 
																						result_set_3.getBoolean("pending_status"));
					suggestionReceiverList.add(suggestionReceiver);
				}
				Suggestion suggestion = suggestionCreatorImpl.createSuggestion(suggestionId, 
																			senderAccount, 
																			suggestionReceiverList, 
																			transactionIdList, 
																			result_set.getDate("date_of_suggestion"), 
																			result_set.getFloat("net_value"));
				suggestionList.add(suggestion);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return suggestionList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getAll() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : SuggestionDaoImpl.getAll() for Exception");
			e.printStackTrace();
			return null;
		}	
	}
}