package com.project.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.project.dao.AbstractDao;
import com.project.dao.TransactionDao;
import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;
import com.project.manager.impl.TransactionCreatorImpl;

public class TransactionDaoImpl
	extends AbstractDao implements TransactionDao
{
	AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
	TransactionCreatorImpl transactionCreatorImpl = new TransactionCreatorImpl();
	
	public ArrayList<Transaction> getBySender(Account account){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		ArrayList<Transaction> transactionList =new ArrayList<Transaction>();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2;
			sql_stmt = "SELECT * FROM "+queried_username+"_Transactions WHERE sender = "+account.getUsername();
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
				//we need transactionId,sender Account,receiver Account,dateOfCreation,amount,tagList,description,groupNames
				
				Account receiverAccount = accountDaoImpl.getByUsername(result_set.getString("receiver"));
				sql_stmt_2 = "SELECT group_name from "+queried_username+"_Group_Transactions WHERE transaction_id = "
							+result_set.getString("transaction_id");
				ResultSet result_set_2 = statement.executeQuery(sql_stmt);
				ArrayList<String> groupNames = new ArrayList<String>();
				while(result_set_2.next()){
					groupNames.add(result_set_2.getString("group_name"));
				}
				Transaction transaction = transactionCreatorImpl.createTransactionWithGroupNames(result_set.getString("transaction_id"),
																							account,receiverAccount,
																							result_set.getDate("date_of_transaction"),
																							result_set.getFloat("amount"),
																							result_set.getString("tag_list"),
																							result_set.getString("description"),
																							groupNames);
				transactionList.add(transaction);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return transactionList;
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Transaction> getByReceiver(Account account){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		ArrayList<Transaction> transactionList =new ArrayList<Transaction>();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2;
			sql_stmt = "SELECT * FROM "+queried_username+"_Transactions WHERE receiver = "+account.getUsername();
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
				//we need transactionId,sender Account,receiver Account,dateOfCreation,amount,tagList,description,groupNames
				
				Account senderAccount = accountDaoImpl.getByUsername(result_set.getString("sender"));
				sql_stmt_2 = "SELECT group_name from "+queried_username+"_Group_Transactions WHERE transaction_id = "+result_set.getString("transaction_id");
				ResultSet result_set_2 = statement.executeQuery(sql_stmt);
				ArrayList<String> groupNames = new ArrayList<String>();
				while(result_set_2.next()){
					groupNames.add(result_set_2.getString("group_name"));
				}
				Transaction transaction = transactionCreatorImpl.createTransactionWithGroupNames(result_set.getString("transaction_id"),
																							senderAccount,account,
																							result_set.getDate("date_of_transaction"),
																							result_set.getFloat("amount"),
																							result_set.getString("tag_list"),
																							result_set.getString("description"),
																							groupNames);
				transactionList.add(transaction);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return transactionList;
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Transaction> getByDate(Account account, Date date){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		ArrayList<Transaction> transactionList =new ArrayList<Transaction>();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2;
			sql_stmt = "SELECT * FROM "+queried_username+"_Transactions WHERE date_of_transaction = "+date;
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
				//we need transactionId,sender Account,receiver Account,dateOfCreation,amount,tagList,description,groupNames
				Account senderAccount = accountDaoImpl.getByUsername(result_set.getString("sender"));
				Account receiverAccount = accountDaoImpl.getByUsername(result_set.getString("receiver"));
				sql_stmt_2 = "SELECT group_name from "+queried_username+"_Group_Transactions WHERE transaction_id = "+result_set.getString("transaction_id");
				ResultSet result_set_2 = statement.executeQuery(sql_stmt);
				ArrayList<String> groupNames = new ArrayList<String>();
				while(result_set_2.next()){
					groupNames.add(result_set_2.getString("group_name"));
				}
				Transaction transaction = transactionCreatorImpl.createTransactionWithGroupNames(result_set.getString("transaction_id"),
																							senderAccount,receiverAccount,
																							result_set.getDate("date_of_transaction"),
																							result_set.getFloat("amount"),
																							result_set.getString("tag_list"),
																							result_set.getString("description"),
																							groupNames);
				transactionList.add(transaction);
			}
			
			result_set.close();
			statement.close();
			conn.close();
			return transactionList;
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Transaction> getByTag(Account account, String tag){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		ArrayList<Transaction> transactionList =new ArrayList<Transaction>();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2;
			sql_stmt = "SELECT * FROM "+queried_username+"_Transactions WHERE tag_list = "+tag;
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
				//we need transactionId,sender Account,receiver Account,dateOfCreation,amount,tagList,description,groupNames
				
				Account senderAccount = accountDaoImpl.getByUsername(result_set.getString("sender"));
				Account receiverAccount = accountDaoImpl.getByUsername(result_set.getString("receiver"));
				sql_stmt_2 = "SELECT group_name from "+queried_username+"_Group_Transactions WHERE transaction_id = "+result_set.getString("transaction_id");
				ResultSet result_set_2 = statement.executeQuery(sql_stmt);
				ArrayList<String> groupNames = new ArrayList<String>();
				while(result_set_2.next()){
					groupNames.add(result_set_2.getString("group_name"));
				}
				Transaction transaction = transactionCreatorImpl.createTransactionWithGroupNames(result_set.getString("transaction_id"),
																							senderAccount,receiverAccount,
																							result_set.getDate("date_of_transaction"),
																							result_set.getFloat("amount"),
																							result_set.getString("tag_list"),
																							result_set.getString("description"),
																							groupNames);
				transactionList.add(transaction);
			}
			result_set.close();
			statement.close();
			conn.close();
			return transactionList;
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Transaction> getByGroup(Group group){
		
		System.out.println("In Function : AccountDaoImpl.getByUsername()");
		String queried_username = group.getAccount().getUsername();
		String groupName = group.getGroupName();
		ArrayList<Transaction> transactionList =new ArrayList<Transaction>();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2,sql_stmt_3;
			sql_stmt = "SELECT transaction_id FROM "+queried_username+"_Group_Transactions WHERE group_name = "+groupName;
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
				sql_stmt_2 = "SELECT * FROM "+queried_username+"_Transactions WHERE transaction_id = "+result_set.getString("transaction_id"); 
				ResultSet result_set_2 = statement.executeQuery(sql_stmt_2);
				while(result_set_2.next()){
					//we need transactionId,sender Account,receiver Account,dateOfCreation,amount,tagList,description,groupNames
					Account senderAccount = accountDaoImpl.getByUsername(result_set_2.getString("sender"));
					Account receiverAccount = accountDaoImpl.getByUsername(result_set_2.getString("receiver"));
					sql_stmt_3 = "SELECT group_name from "+queried_username+"_Group_Transactions WHERE transaction_id = "+result_set.getString("transaction_id");
					ResultSet result_set_3 = statement.executeQuery(sql_stmt);
					ArrayList<String> groupNames = new ArrayList<String>();
					while(result_set_3.next()){
						groupNames.add(result_set_3.getString("group_name"));
					}
					Transaction transaction = transactionCreatorImpl.createTransactionWithGroupNames(result_set.getString("transaction_id"),
																								senderAccount,receiverAccount,
																								result_set_2.getDate("date_of_transaction"),
																								result_set_2.getFloat("amount"),
																								result_set_2.getString("tag_list"),
																								result_set_2.getString("description"),
																								groupNames);
					transactionList.add(transaction);
				}
			}
			result_set.close();
			statement.close();
			conn.close();
			return transactionList;
		}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public Transaction getById(Account account, String transactionId){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2;
			sql_stmt = "SELECT * FROM "+queried_username+"_Transactions WHERE transaction_id = "+transactionId;
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
			Account senderAccount = accountDaoImpl.getByUsername(result_set.getString("sender"));
			Account receiverAccount = accountDaoImpl.getByUsername(result_set.getString("receiver"));
			sql_stmt_2 = "SELECT group_name from "+queried_username+"_Group_Transactions WHERE transaction_id = "+transactionId;
			ResultSet result_set_2 = statement.executeQuery(sql_stmt);
			ArrayList<String> groupNames = new ArrayList<String>();
			while(result_set_2.next()){
				groupNames.add(result_set_2.getString("group_name"));
			}
			Transaction transaction = transactionCreatorImpl.createTransactionWithGroupNames(transactionId,
																						senderAccount,receiverAccount,
																						result_set.getDate("date_of_transaction"),
																						result_set.getFloat("amount"),
																						result_set.getString("tag_list"),
																						result_set.getString("description"),
																						groupNames);
			result_set.close();
			statement.close();
			conn.close();
			return transaction;	
	    }
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public String addTransaction(Transaction transaction, Account account){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		ResultSet result_set_random;
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    String sql_random = "SELECT CONVERT(SUBSTRING(MD5(RAND()) FROM 1 FOR 10) USING latin1) AS random_id";
		    result_set_random = statement.executeQuery(sql_random);
		    result_set_random.next();
			PreparedStatement sql_stmt = conn.prepareStatement("INSERT INTO "+queried_username+"_Transactions VALUES (?,?,?,?,?,?)");
			sql_stmt.setString(1,result_set_random.getString("random_id"));
			sql_stmt.setString(2,transaction.getSender().getUsername());
			sql_stmt.setString(3,transaction.getReceiver().getUsername());
			sql_stmt.setFloat(4,transaction.getAmount());
			sql_stmt.setString(5,transaction.getDescription());//NOTE : Is There no way to set the Text? Do we have to go for string?
			sql_stmt.setString(6,transaction.getTagList());
			sql_stmt.executeUpdate();
		
			statement.close();
			conn.close();
			return result_set_random.getString("random_id");
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean removeTransaction(Transaction transaction, Account account){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2,sql_stmt_3;
			sql_stmt = "DELETE FROM "+queried_username+"_Transactions WHERE transaction_id = "+transaction.getTransactionId();
			statement.executeUpdate(sql_stmt);
			statement.close();
			conn.close();
			return true;
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean modifyTransaction(Transaction transaction,String propertyName,Account account){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
			conn = getConnectionForUser(DB_URL);
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt;
			if(propertyName.equals("amount")){
				sql_stmt = "UPDATE TABLE "+queried_username+"_Transactions SET amount = "+transaction.getAmount();
			}
			else{
				if(propertyName.equals("description")){
					sql_stmt = "UPDATE TABLE "+queried_username+"_Transactions SET description = "+transaction.getDescription();
				}
				else{
					sql_stmt = "UPDATE TABLE "+queried_username+"_Transactions SET tag_list = "+transaction.getTagList();
				}
			}
			statement.executeUpdate(sql_stmt);
		
			statement.close();
			conn.close();
			return true;
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return false;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Transaction> getTransactions(Account account){
		
		System.out.println("In Function : TransactionDaoImpl.getByUsername()");
		String queried_username = account.getUsername();
		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
		try{
			System.out.println("Inside Try Block of Function : TransactionDaoImpl.getByUsername()");
		    System.out.println("Creating statement...");
		    statement = conn.createStatement();
			String sql_stmt,sql_stmt_2;
			sql_stmt = "SELECT * FROM "+queried_username+"_Transactions";
			ResultSet result_set = statement.executeQuery(sql_stmt);
			
			while(result_set.next()){
				//we need transactionId,sender Account,receiver Account,dateOfCreation,amount,tagList,description,groupNames
				
				Account senderAccount = accountDaoImpl.getByUsername(result_set.getString("sender"));
				Account receiverAccount = accountDaoImpl.getByUsername(result_set.getString("receiver"));
				sql_stmt_2 = "SELECT group_name from "+queried_username+"_Group_Transactions WHERE transaction_id = "+result_set.getString("transaction_id");
				ResultSet result_set_2 = statement.executeQuery(sql_stmt);
				ArrayList<String> groupNames = new ArrayList<String>();
				while(result_set_2.next()){
					groupNames.add(result_set_2.getString("group_name"));
				}
				Transaction transaction = transactionCreatorImpl.createTransactionWithGroupNames(result_set.getString("transaction_id"),
																							senderAccount,receiverAccount,
																							result_set.getDate("date_of_transaction"),
																							result_set.getFloat("amount"),
																							result_set.getString("tag_list"),
																							result_set.getString("description"),
																							groupNames);
				transactionList.add(transaction);
			}
		
			statement.close();
			conn.close();
			return transactionList;
	}
		catch(SQLException se){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for SQLException");
			se.printStackTrace();
			return null;
		}
		catch(Exception e){
			System.out.println("Inside Catch Block of Function : TransactionDaoImpl.getByUsername() for Exception");
			e.printStackTrace();
			return null;
		}
	}
}