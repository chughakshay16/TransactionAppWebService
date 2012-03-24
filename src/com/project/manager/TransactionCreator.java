package com.project.manager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;

public interface TransactionCreator 
{
	public Transaction createTransaction(String transactionId,Account sender,Account receiver,Date doc,float amount,String taglist,String description,ArrayList<Group> groups);
	public Transaction createTransaction(ResultSet transaction);
	public ArrayList<Transaction> createTransactions(ResultSet transactions,ResultSet groups);

}
