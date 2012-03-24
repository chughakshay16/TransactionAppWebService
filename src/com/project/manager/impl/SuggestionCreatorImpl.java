package com.project.manager.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.project.dom.Account;
import com.project.dom.Suggestion;
import com.project.dom.SuggestionReceiver;
import com.project.dom.Transaction;
import com.project.manager.SuggestionCreator;

public class SuggestionCreatorImpl implements SuggestionCreator {

	private ArrayList<Suggestion> suggestions;
	public SuggestionCreatorImpl(){}
	@Override
	public Suggestion createSuggestion(String suggestionId, Account sender,
			ArrayList<SuggestionReceiver> receivers, String[] transactionIds,
			float netValue) {
		// TODO Auto-generated method stub
		return null;
	}
	//this method sets all the properties of the suggestion object
	@Override
	public Suggestion createSuggestion(Account sender,ArrayList<Transaction> transactions,float netValue) {
		// TODO Auto-generated method stub
		ArrayList<Account> receivers=new ArrayList<Account>();
		boolean senderFlag=false;
		boolean receiverFlag=false;
		ArrayList<String> transactionIds=new ArrayList<String>();
		// now we will find all the receivers
		for(int i=0;i<transactions.size();i++)
		{
			transactionIds.add(transactions.get(i).getTransactionId());
			Account tempSender=transactions.get(i).getSender();
			Account tempReceiver=transactions.get(i).getReceiver();
			if(!(tempSender.getUsername().equals(sender.getUsername())))
					{
						if(receivers.size()==0)
						{
							receivers.add(tempSender);
						}
						else 
						{
							for(int j=0;j<receivers.size();j++)
							{
								if(receivers.get(j).getUsername().equals(tempSender.getUsername()))
									senderFlag=true;
							}
						if(!senderFlag)
							receivers.add(tempSender);
						}
					}
			if(!(tempReceiver.getUsername().equals(sender.getUsername())))
					{
						if(receivers.size()==0)
						{
							receivers.add(tempReceiver);
						}
						else 
						{
							for(int j=0;j<receivers.size();j++)
							{
								if(receivers.get(j).getUsername().equals(tempReceiver.getUsername()))
									receiverFlag=true;
							}
						if(!receiverFlag)
							receivers.add(tempReceiver);
						}
					}
		}
		ArrayList<SuggestionReceiver> receiversRef=new ArrayList<SuggestionReceiver>();
		for(int k=0;k<receivers.size();k++)
		{
			SuggestionReceiver receiver=new SuggestionReceiver(receivers.get(k),false,true);
			receiversRef.add(receiver);
		}
		return new Suggestion(null, sender, receiversRef, transactionIds, netValue);
		
		
	}

	@Override
	public ArrayList<Suggestion> createSuggestions(ResultSet suggestions,
			ResultSet suggestionsDetails, ResultSet suggestionsTransactions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Suggestion> createSuggestion(ResultSet suggestions,
			ResultSet suggestionsDetails, ResultSet suggestionsTransactions) {
		// TODO Auto-generated method stub
		return null;
	}

	public Suggestion createSuggestion(String suggestionId,Account sender,ArrayList<SuggestionReceiver> receivers,ArrayList<String> transactionIds,Date dateOfSuggestion,float netValue){
		Suggestion suggestion = new Suggestion();
		
		suggestion.setSuggestionId(suggestionId);
		suggestion.setSender(sender);
		suggestion.setReceivers(receivers);
		suggestion.setTransactionIds(transactionIds);
		suggestion.setDateOfSuggestion(dateOfSuggestion);
		suggestion.setNetValue(netValue);
		
		return suggestion;
	}
	
	@Override
	public ArrayList<SuggestionReceiver> createSuggestionReceivers(
			ResultSet suggestionsDetails) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuggestionReceiver createSuggestionReceiver(Account receiver,
			boolean acceptanceStatus, boolean pendingStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuggestionReceiver createSuggestionReceiver(
			ResultSet suggestionsDetails) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSuggestions(ArrayList<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}
	public ArrayList<Suggestion> getSuggestions() {
		return suggestions;
	}

}
