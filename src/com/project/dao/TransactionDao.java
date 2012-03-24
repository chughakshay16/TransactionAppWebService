package com.project.dao;

import java.util.ArrayList;
import java.util.Date;

import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.TransactionNotFoundException;

public interface TransactionDao 
{
	public ArrayList<Transaction> getBySender(Account account);
	public ArrayList<Transaction> getByReceiver(Account account);
	public ArrayList<Transaction> getByDate(Account account,Date date);
	public ArrayList<Transaction> getByTag(Account account,String tag);
	public ArrayList<Transaction> getByGroup(Group group);
	public Transaction getById(Account account,String transactionId);
	//public String addTransaction(Transaction transaction);
	public String addTransaction(Transaction transaction,Account account);
	public boolean removeTransaction(Transaction transaction,Account account)throws TransactionNotFoundException;
	public boolean modifyTransaction(Transaction transaction, String propertyName,Account account)throws TransactionNotFoundException,AccountNotFoundException;
	public ArrayList<Transaction> getTransactions(Account userAccount)throws AccountNotFoundException;
	
}
