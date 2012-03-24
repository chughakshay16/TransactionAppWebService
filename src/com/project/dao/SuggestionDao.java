package com.project.dao;

import java.util.ArrayList;
import java.util.Date;

import com.project.dom.Account;
import com.project.dom.Notification;
import com.project.dom.Suggestion;
import com.project.dom.SuggestionReceiver;
import com.project.exceptions.AccountNotFoundException;
import com.project.exceptions.SuggestionNotFoundException;

public interface SuggestionDao 
{
	//all getter methods will return suggestionIds
	public ArrayList<Suggestion> getBySender(Account account);
	public ArrayList<Suggestion> getByPendingStatus(boolean status);
	public ArrayList<Suggestion> getByDateOfSuggestion(Date date);
	public ArrayList<Suggestion> getByAcceptanceStatus(boolean status);
	public ArrayList<SuggestionReceiver> getReceiversPerSuggestion(String suggestionId);
	public String addSuggestion(Suggestion suggestion);//this method makes changes to all the 3 suggestion tables
	public boolean removeSuggestion(Suggestion suggestion); 
	public boolean modifySuggestionReceiver(Notification notification,String propertyName)throws SuggestionNotFoundException;
	public ArrayList<Suggestion> getAll(Account sender)throws AccountNotFoundException;
}
