package com.project.dom;

import java.util.ArrayList;
import java.util.Date;



public class Suggestion 
{
	private String suggestionId;
	private Account sender;
	private ArrayList<SuggestionReceiver> receivers;
	private ArrayList<String> transactionIds;
	private Date dateOfSuggestion;
	private float netValue;
	public Suggestion()
	{
		suggestionId=null;
		sender=null;
		receivers=new ArrayList<SuggestionReceiver>();
		transactionIds=new ArrayList<String>();
		dateOfSuggestion=new Date();
		netValue=0;
	}
	public Suggestion(String suggestionId,Account sender,ArrayList<SuggestionReceiver> receivers,ArrayList<String> transactionIds,float netValue)
	{
		this.suggestionId=null;
		this.sender=sender;
		this.receivers=receivers;
		this.transactionIds=transactionIds;
		this.dateOfSuggestion=new Date();
		this.netValue=netValue;
	}
	public void setAcceptanceStatus(Account receiverAccount,boolean acceptanceStatus)
	{
		for(int i=0;i<receivers.size();i++)
		{
			if(receivers.get(i).getReceiver().getUsername().equals(receiverAccount.getUsername()))
			{
				SuggestionReceiver temp=receivers.get(i);
				temp.setAcceptanceStatus(acceptanceStatus);
				receivers.set(i, temp);
			}
		}
	}
	public void setSuggestionId(String suggestionId) {
		this.suggestionId = suggestionId;
	}
	public String getSuggestionId() {
		return suggestionId;
	}
	public void setSender(Account sender) {
		this.sender = sender;
	}
	public Account getSender() {
		return sender;
	}
	public void setReceivers(ArrayList<SuggestionReceiver> receivers) {
		this.receivers = receivers;
	}
	public ArrayList<SuggestionReceiver> getReceivers() {
		return receivers;
	}
	public void setTransactionIds(ArrayList<String> transactionIds) {
		this.transactionIds = transactionIds;
	}
	public ArrayList<String> getTransactionIds() {
		return transactionIds;
	}
	public void setDateOfSuggestion(Date dateOfSuggestion) {
		this.dateOfSuggestion = dateOfSuggestion;
	}
	public Date getDateOfSuggestion() {
		return dateOfSuggestion;
	}
	public void setNetValue(float netValue) {
		this.netValue = netValue;
	}
	public float getNetValue() {
		return netValue;
	}
	
	
}
