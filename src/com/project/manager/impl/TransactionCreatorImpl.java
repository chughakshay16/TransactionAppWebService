package com.project.manager.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.project.dom.Account;
import com.project.dom.Group;
import com.project.dom.Transaction;
import com.project.manager.TransactionCreator;
@XmlRootElement(name="collection")
public class TransactionCreatorImpl implements TransactionCreator {
	//we have set this field for use in marshalling of collection of transaction objects
	private ArrayList<Transaction> transactions;
	public TransactionCreatorImpl(){}
	@Override
	public Transaction createTransaction(String transactionId, Account sender,
			Account receiver, Date doc, float amount, String taglist,
			String description, ArrayList<Group> groups) {
		// TODO Auto-generated method stub
		ArrayList<String> groupNames=new ArrayList<String>();
		for(int i=0;i<groups.size();i++)
		{
			groupNames.add(groups.get(i).getGroupName());
		}
		return new Transaction(transactionId,sender,receiver,amount,taglist,description,groupNames);
		
	}

	@Override
	public Transaction createTransaction(ResultSet transaction) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Transaction createTransactionWithGroupNames(String transactionId,Account sender,Account receiver,Date dateOfCreation,float amount,String tagList,String description,ArrayList<String> groupNames){
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDateOfCreation(dateOfCreation);
		transaction.setDescription(description);
		transaction.setGroupNames(groupNames);
		transaction.setReceiver(receiver);
		transaction.setSender(sender);
		transaction.setTagList(tagList);
		transaction.setTransactionId(transactionId);
		
		return transaction;
	}

	@Override
	public ArrayList<Transaction> createTransactions(ResultSet transactions,
			ResultSet groups) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	@XmlElementWrapper(name="transactions")
	@XmlElement(name="transaction")
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

}
