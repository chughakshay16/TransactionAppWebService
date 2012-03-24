package com.project.manager;


import java.sql.ResultSet;
import java.util.ArrayList;

import com.project.dom.Account;
import com.project.dom.Suggestion;
import com.project.dom.SuggestionReceiver;
import com.project.dom.Transaction;

public interface SuggestionCreator 
{
	public Suggestion createSuggestion(String suggestionId,Account sender,ArrayList<SuggestionReceiver> receivers,String[] transactionIds,float netValue);
	public Suggestion createSuggestion(Account sender,ArrayList<Transaction> transactions,float netValue);
	public ArrayList<Suggestion> createSuggestions(ResultSet suggestions, ResultSet suggestionsDetails,ResultSet suggestionsTransactions);
	public ArrayList<Suggestion> createSuggestion(ResultSet suggestions, ResultSet suggestionsDetails,ResultSet suggestionsTransactions);
	public ArrayList<SuggestionReceiver> createSuggestionReceivers(ResultSet suggestionsDetails);
	public SuggestionReceiver createSuggestionReceiver(Account receiver,boolean acceptanceStatus,boolean pendingStatus);
	public SuggestionReceiver createSuggestionReceiver(ResultSet suggestionsDetails);
}
